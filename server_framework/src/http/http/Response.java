package http.http;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

public class Response {
    private DefaultHttpResponse response;
    private ByteBuf body;

    public Response(DefaultHttpResponse response, ByteBuf body) {
        this.response = response;
        this.body = body;
    }


    public ByteBuf getBody() {
        return body;
    }

    public void setBody(ByteBuf body) {
        this.body = body;
    }

    public String getResult() {
        String result = body.toString(CharsetUtil.UTF_8);
        body.release();
        return result;
    }

    public String getResult(Charset charset) {
        String result = body.toString(charset);
        body.release();
        return result;
    }

    public DefaultHttpResponse getResponse() {
        return response;
    }

    public void setResponse(DefaultHttpResponse response) {
        this.response = response;
    }
}