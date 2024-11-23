package org.example.system;

import org.example.entities.ControllerMeta;
import org.example.entities.RequestInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.HashMap;

public class ApplicationLoader {
    private static ApplicationLoader instance = null;
    private ClassLoader classLoader = ClassLoader.getSystemClassLoader();
    private HashMap<RequestInfo, ControllerMeta> controllerLookUpTable = new HashMap<RequestInfo, ControllerMeta>();
    private HashMap<Class, Object> injectableLookUpTable = new HashMap<>();

    private ApplicationLoader() {
    }

    public static ApplicationLoader getInstance() {
        if (instance == null) {
            instance = new ApplicationLoader();
        }

        return instance;
    }

    public HashMap<RequestInfo, ControllerMeta> getControllerTable() {
        return this.controllerLookUpTable;
    }

    public ControllerMeta getController(RequestInfo requestInfo) {
        return  this.controllerLookUpTable.get(requestInfo);
    }

    public Object getInjectable(Class classKey, boolean isSingleton) {
        var resultInstance = this.injectableLookUpTable.get(classKey);

        if (resultInstance == null || !isSingleton) {
            try {
                resultInstance = classKey.getDeclaredConstructor().newInstance();
                this.injectableLookUpTable.put(classKey, resultInstance);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return resultInstance;
    }

    public void findAllClasses(String packageName) throws IOException, ClassNotFoundException {
        var classLoaderStream = this.classLoader.getResourceAsStream(
            packageName.replace('.', '/'));
        var classReader = new BufferedReader(new InputStreamReader(classLoaderStream));

        String packageReference;
        while ((packageReference = classReader.readLine()) != null) {
            if (!packageReference.contains(".class")) {
                this.findAllClasses(packageName + "." + packageReference);
            } else if (packageReference.contains(".class")) {
                this.classParser(packageReference, packageName);
            }
        }
    }

    private void classParser(String packageReference, String packageName) throws ClassNotFoundException {
        String className = packageReference.replace(".class", "");
        String fullClassName = packageName + "." + className;
        var currentClass = Class.forName(fullClassName);

        if (currentClass.isAnnotationPresent(org.example.stereotypes.Controller.class)) {
            this.parseController(currentClass);
        } else if (currentClass.isAnnotationPresent(org.example.stereotypes.Injectable.class)) {
            this.parseInjectable(currentClass);
        }
    }

    private void parseInjectable(Class currentClass) {
        this.injectableLookUpTable.put(currentClass, null);
    }

    private void parseController(Class currentClass) {
        var methods = currentClass.getMethods();

        for (Method method : methods) {
            this.processMappingAnnotation(method, currentClass);
        }
    }

    private void processMappingAnnotation(Method method, Class currentClass) {
        String httpMethod = null;
        String endpoint = null;

        if (method.isAnnotationPresent(org.example.stereotypes.GetMapping.class)) {
            httpMethod = "GET";
            endpoint = method.getAnnotation(org.example.stereotypes.GetMapping.class).value();
        } else if (method.isAnnotationPresent(org.example.stereotypes.PostMapping.class)) {
            httpMethod = "POST";
            endpoint = method.getAnnotation(org.example.stereotypes.PostMapping.class).value();
        } else if (method.isAnnotationPresent(org.example.stereotypes.PutMapping.class)) {
            httpMethod = "PUT";
            endpoint = method.getAnnotation(org.example.stereotypes.PutMapping.class).value();
        } else if (method.isAnnotationPresent(org.example.stereotypes.DeleteMapping.class)) {
            httpMethod = "DELETE";
            endpoint = method.getAnnotation(org.example.stereotypes.DeleteMapping.class).value();
        }

        if (httpMethod != null) {
            String methodName = method.getName();

            var parameters = method.getParameters();
            HashMap<Integer, String> pathVariableIndex = new HashMap<>();

            for (int i = 0; i < parameters.length; i++) {
                var parameter = parameters[i];
                // get type here

                if (parameter.isAnnotationPresent(org.example.stereotypes.PathVariable.class)) {
                    var pathVariable = parameter.getAnnotation(org.example.stereotypes.PathVariable.class);
                    // object here value, type
                    pathVariableIndex.put(i, pathVariable.value());
                }
            }

            this.controllerLookUpTable.put(
                new RequestInfo(httpMethod, endpoint),
                new ControllerMeta(currentClass, methodName, pathVariableIndex)
            );
        }
    }
}
