package org.example.system;

import org.example.entities.ControllerMeta;
import org.example.entities.RequestInfo;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class HttpProcessor {
    private ApplicationLoader appLoader = ApplicationLoader.getInstance();

    public String executeController(RequestInfo httpRequest) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
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

        var controllerInstance = currentClass
            .getDeclaredConstructor()
            .newInstance();

        // debugni tuka
        var methodSignature = this.buildMethodParameterTypes(controllerMethodReference);
        var arguments = this.buildMethodArguments(controllerMethodReference, httpRequest);

        return (String) currentClass
            .getMethod(methodName, methodSignature)
            .invoke(controllerInstance, arguments);
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
