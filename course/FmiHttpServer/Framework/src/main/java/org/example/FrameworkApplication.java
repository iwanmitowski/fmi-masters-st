package org.example;

import org.example.entities.RequestInfo;
import org.example.system.ApplicationLoader;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;

public class FrameworkApplication {
    private static final String NEW_LINE = "\r\n";

    private static ApplicationLoader appLoader = new ApplicationLoader();

    public static void run(Class mainClass) {
        try {
            bootstrap(mainClass);
            startWebServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void bootstrap(Class mainClass) throws IOException, ClassNotFoundException {
        appLoader.findAllClasses(mainClass.getPackageName());
    }

    private static RequestInfo parseHTTPRequest(InputStream stream) throws IOException {
        InputStreamReader reader = new InputStreamReader(stream);
        BufferedReader httpRequestReader = new BufferedReader(reader);

        String currentLine;
        String httpMethod = "";
        String httpEndpoint = "";
        boolean isBodyProcessable = false;
        var httpRequest = new RequestInfo(httpMethod, httpEndpoint);

        while((currentLine = httpRequestReader.readLine()) != null) {
            if (currentLine.isEmpty()) {
                isBodyProcessable = true;
                break;
            }

            if (!httpRequest.hasMethodAndEndpoint()) {
                var httpHeaderTitleCollection = currentLine.split(" ");
                httpRequest.setHttpMethod(httpHeaderTitleCollection[0]);
                httpRequest.setHttpEndpoint(httpHeaderTitleCollection[1]);

                continue;
            }

            var headerParseCollection = currentLine.split(": ");
            var headerKey = headerParseCollection[0];
            var headerValue = headerParseCollection[1];
            httpRequest.setHeader(headerKey, headerValue);
        }

        if (isBodyProcessable && httpRequest.hasContent()) {
            var legnth = httpRequest.getContentLength();
            var bodyCharacterCollection = new char[legnth];
            httpRequestReader.read(bodyCharacterCollection, 0, legnth);

            var bodyBuilder = new StringBuilder();
            bodyBuilder.append(bodyCharacterCollection);
            httpRequest.setHttpBody(bodyBuilder.toString());
        }

        return httpRequest;
    }

    private static void startWebServer() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ServerSocket serverSocket = new ServerSocket(1423);
        System.out.println("Listening on port 1423");

        while(serverSocket.isBound()) {
            Socket clientSocket =  serverSocket.accept();

            InputStream request =  clientSocket.getInputStream();
            OutputStream response = clientSocket.getOutputStream();

            var requestInfo = parseHTTPRequest(request);

            String controllerMessage =
                appLoader.executeController(requestInfo);

            String message = buildHTTPResponse(controllerMessage);
            response.write(message.getBytes());

            response.close();
            request.close();
            clientSocket.close();
        }
    }

    private static String buildHTTPResponse(String body) {
        return "HTTP/1.1 200 OK" + NEW_LINE +
            "Access-Control-Allow-Origin: *" + NEW_LINE +
            "Content-Length: " + (body.getBytes().length + 1) +  NEW_LINE +
            "Content-Type: text/html" + NEW_LINE + NEW_LINE +
            body + NEW_LINE + NEW_LINE;
    }
}
