package org.example.system;

import org.example.entities.ControllerMeta;
import org.example.entities.RequestInfo;

public class HttpProcessor {
    private ApplicationLoader appLoader = ApplicationLoader.getInstance();

    public String executeController(RequestInfo httpRequest) throws Exception {
        var httpMethod = httpRequest.getHttpMethod();
        var httpRequestEndpoint = httpRequest.getHttpEndpoint();
        ControllerMeta controllerMethodReference = null;

        for (RequestInfo controlerRequestInfo : this.appLoader.getControllerTable().keySet()) {
            if (controlerRequestInfo.isProcessable(httpMethod, httpRequestEndpoint)){
                controllerMethodReference = appLoader.getController(controlerRequestInfo);
                httpRequest.setPathVariables(controlerRequestInfo.getPathVariables());

                break;
            }
        }

        if (controllerMethodReference == null) {
            return "Internal server error";
        }

        var currentClass = controllerMethodReference.getClassReference();
        var methodName = controllerMethodReference.getMethodName();

        // Pri DI Ctor
        var controllerInstance = currentClass
            .getDeclaredConstructor()
            .newInstance();

        // debugni tuka
        var methodSignature = this.buildMethodParameterTypes(controllerMethodReference);
        var arguments = this.buildMethodArguments(controllerMethodReference, httpRequest);

        var fieldCollection = currentClass.getDeclaredFields();
        for (var field : fieldCollection) {
            if (field.isAnnotationPresent(org.example.stereotypes.Autowired.class)) {
                field.setAccessible(true);

                var injectableMaterial = field.getType();
                var isSingleton = field.getAnnotation(org.example.stereotypes.Autowired.class).isSingleton();

                // Get the top-level instance
                var injectableInstance = this.appLoader.getInjectable(injectableMaterial, isSingleton);

                // Recursively set fields for the instance (handles nested injection)
                this.processAutowiredServices(injectableInstance);

                // Set the initialized instance to the current field
                field.set(controllerInstance, injectableInstance);
            }
        }

        return (String) currentClass
            .getMethod(methodName, methodSignature)
            .invoke(controllerInstance, arguments);
    }

    private void processAutowiredServices(Object instance) throws Exception {
        var fields = instance.getClass().getDeclaredFields();
        for (var field : fields) {
            if (field.isAnnotationPresent(org.example.stereotypes.Autowired.class)) {
                field.setAccessible(true);

                var injectableMaterial = field.getType();
                var isSingleton = field.getAnnotation(org.example.stereotypes.Autowired.class).isSingleton();
                var injectableInstance = this.appLoader.getInjectable(injectableMaterial, isSingleton);
                if (injectableMaterial.isAnnotationPresent(org.example.stereotypes.Injectable.class)) {
                    var nestedInstance = injectableMaterial.getDeclaredConstructor().newInstance();
                    processAutowiredServices(nestedInstance);
                    field.set(instance, nestedInstance);
                } else {
                    field.set(instance, injectableInstance);
                }
            }
        }
    }


    private Class<?>[] buildMethodParameterTypes(ControllerMeta controllerMeta) {
        var pathVariableIndex = controllerMeta.getPathVariableIndex();
        var parameterTypes = new Class<?>[pathVariableIndex.size()];

        for (int index: pathVariableIndex.keySet()) {
            parameterTypes[index] = Integer.class;
        }

        return parameterTypes;
    }

    private Object[] buildMethodArguments(ControllerMeta controllerMeta, RequestInfo requestInfo) {
        var pathVariableIndex = controllerMeta.getPathVariableIndex();
        var pathVariables = requestInfo.getPathVariables();
        var arguments = new Object[pathVariableIndex.size()];

        for (int index: pathVariableIndex.keySet()) {
            var variableName = pathVariableIndex.get(index);
            var variableValue = pathVariables.get(variableName);

            if (variableValue != null) {
                arguments[index] = Integer.parseInt(variableValue);
            }

        }

        return arguments;
    }
}
