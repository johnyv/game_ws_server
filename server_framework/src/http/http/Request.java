package http.http;

import http.future.Future;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.HashMap;

public class Request {
    public static enum RequestMethod {
        GET, POST
    }

    private Future future;
    private String path;
    private HashMap<String, String> headers = new HashMap<String, String>();
    private RequestMethod method;
    private ByteBuf body;

    public Request(String path, RequestMethod method) {
        this.path = path;
        this.method = method;
    }


    public Future getFuture() {
        return future;
    }

    public void setFuture(Future future) {
        this.future = future;
    }

    public void writeBody(byte[] bytes) {
        if (body == null) {
            body = Unpooled.buffer();
        }
        body.writeBytes(bytes);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public void setMethod(RequestMethod method) {
        this.method = method;
    }

    public ByteBuf getBody() {
        return body;
    }

    public void setBody(ByteBuf body) {
        this.body = body;
    }

    public void addHeader(String name, String value) {
        this.headers.put(name, value);
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }
}