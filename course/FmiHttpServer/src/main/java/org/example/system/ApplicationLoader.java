package org.example.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Objects;

public class ApplicationLoader {
    public static class RequestInfo {
        private String httpMethod;
        private String httpEndpoint;

        public RequestInfo(String httpMethod, String httpEndpoint) {
            this.httpMethod = httpMethod;
            this.httpEndpoint = httpEndpoint;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.httpMethod, httpEndpoint);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }

            RequestInfo requestInfo = (RequestInfo)obj;

            return requestInfo.httpEndpoint.equals(this.httpEndpoint) &&
                requestInfo.httpMethod.equals(this.httpMethod);
        }
    }

    private ClassLoader classLoader = ClassLoader.getSystemClassLoader();
    private HashMap<RequestInfo, Class> controllerLookUpTable = new HashMap<RequestInfo, Class>();

    public String executeController(String httpMethod, String httpEndpoint) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        var currentClass = this.controllerLookUpTable.get(new RequestInfo(httpMethod, httpEndpoint));

        if (currentClass == null) {
            return "";
        }

        var controllerInstance = currentClass.getDeclaredConstructor().newInstance();

        return (String) currentClass
            .getMethod("index")
            .invoke(controllerInstance);
    }

    public void findAllClasses(String packageName) throws IOException, ClassNotFoundException {
        var classLoaderStream = this.classLoader.getResourceAsStream(
            packageName.replace('.', '/'));
        var classReader = new BufferedReader(new InputStreamReader(classLoaderStream));

        String packageReference;
        while ((packageReference = classReader.readLine()) != null) {
            if (!packageReference.contains(".class")) {
                findAllClasses(packageName + "." + packageReference);
            } else if (packageReference.contains(".class")) {
                String className = packageReference.replace(".class", "");
                String fullClassName = packageName + "." + className;
                var currentClass = Class.forName(fullClassName);

                if (currentClass.isAnnotationPresent(org.example.stereotypes.Controller.class)) {
                    var annotation = currentClass.getAnnotation(org.example.stereotypes.Controller.class);
                    String httpMethod = annotation.method();
                    String httpEndpoint = annotation.endpoint();

                    this.controllerLookUpTable.put(
                        new RequestInfo(httpMethod, httpEndpoint),
                        currentClass
                    );
                }
            }
        }
    }
}
