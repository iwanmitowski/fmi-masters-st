package org.example;

import org.example.controllers.CustomerController;
import org.example.controllers.HomeController;
import org.example.system.ApplicationLoader;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    private static final String NEW_LINE = "\n\r";

    public static void main(String[] args) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        var appLoader = new ApplicationLoader();
        appLoader.findAllClasses("org.example");

        ServerSocket serverSocket   = new ServerSocket(1423);

        while(serverSocket.isBound()) {
            Socket clientSocket         =  serverSocket.accept();

            InputStream request     =  clientSocket.getInputStream();
            OutputStream response   = clientSocket.getOutputStream();

            InputStreamReader reader = new InputStreamReader(request);
            BufferedReader httpRequestReader = new BufferedReader(reader);

            String currentLine;
            String httpMethod = "";
            String httpEndpoint = "";

            while((currentLine = httpRequestReader.readLine()) != null) {
                var httpHeaderTitleCollection = currentLine.split(" ");
                httpMethod = httpHeaderTitleCollection[0];
                httpEndpoint = httpHeaderTitleCollection[1];
                break;
            }

            String controllerMessage =
                appLoader.executeController(httpMethod, httpEndpoint);

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