package org.example.entities;

import java.util.HashMap;
import java.util.Objects;

public class RequestInfo {
    private String httpMethod;
    private String httpEndpoint;
    private String httpBody;
    private HashMap<String, String> headers = new HashMap<>();

    public RequestInfo() {
        this.httpMethod = "";
        this.httpEndpoint = "";
    }

    public RequestInfo(String httpMethod, String httpEndpoint) {
        this.httpMethod = httpMethod;
        this.httpEndpoint = httpEndpoint;
    }

    public boolean hasContent() {
        return this.getContentLength() > 0;
    }

    public boolean hasMethodAndEndpoint() {
        return !this.getHttpMethod().isEmpty()
            && !this.getHttpEndpoint().isEmpty();
    }

    public int getContentLength() {
        var value = this.getHeaderValue("Content-Length");
        if (Objects.isNull(value)) {
            return 0;
        }

        return Integer.parseInt(value);
    }

    public String getHeaderValue(String header) {
        return this.headers.get(header);
    }

    public boolean hasHeader(String header) {
        return this.headers.containsKey(header);
    }

    public void setHeader(String key, String value) {
        this.headers.put(key, value);
    }

    public String getHttpBody() {
        return httpBody;
    }

    public void setHttpBody(String httpBody) {
        this.httpBody = httpBody;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getHttpEndpoint() {
        return httpEndpoint;
    }

    public void setHttpEndpoint(String httpEndpoint) {
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