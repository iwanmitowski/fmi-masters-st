package org.example.system;

import org.example.entities.ControllerMeta;
import org.example.entities.RequestInfo;
import org.example.stereotypes.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class ApplicationLoader {
    private ClassLoader classLoader = ClassLoader.getSystemClassLoader();
    private HashMap<RequestInfo, ControllerMeta> controllerLookUpTable = new HashMap<RequestInfo, ControllerMeta>();

    public String executeController(RequestInfo httpRequest) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        var controllerMethodReference = this.controllerLookUpTable.get(
            new RequestInfo(
                httpRequest.getHttpMethod(),
                httpRequest.getHttpEndpoint()));

        if (controllerMethodReference == null) {
            return "";
        }

        var currentClass = controllerMethodReference.getClassReference();
        var methodName = controllerMethodReference.getMethodName();

        var controllerInstance = currentClass
            .getDeclaredConstructor()
            .newInstance();

        return (String) currentClass
            .getMethod(methodName)
            .invoke(controllerInstance);
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
        }
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
            this.controllerLookUpTable.put(
                new RequestInfo(httpMethod, endpoint),
                new ControllerMeta(currentClass, methodName)
            );
        }
    }
}
