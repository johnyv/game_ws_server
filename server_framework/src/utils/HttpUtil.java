package utils;

import http.client.HttpClientPool;
import http.future.FutureListener;
import http.http.Request;
import http.http.RequestFuture;
import http.http.Response;

import static io.netty.util.internal.StringUtil.EMPTY_STRING;
import static io.netty.util.internal.StringUtil.SPACE;
import static io.netty.util.internal.StringUtil.decodeHexByte;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.DefaultThreadFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpUtil {

    private static NioEventLoopGroup bossEventLoopGroup;
    private static NioEventLoopGroup workerEventLoopGroup;
    private static NioEventLoopGroup requestEventLoopGroup;
    private static HashMap<String, HttpClientPool> poolMap = new HashMap<String, HttpClientPool>();

    public synchronized static void setBossEventLoopGroup(NioEventLoopGroup bossEventLoopGroup) {
        assert HttpUtil.bossEventLoopGroup == null;
        HttpUtil.bossEventLoopGroup = bossEventLoopGroup;
    }

    public synchronized static NioEventLoopGroup getBossEventLoopGroup() {
        if (bossEventLoopGroup == null) {
            bossEventLoopGroup = new NioEventLoopGroup(2, Executors.newFixedThreadPool(2, new DefaultThreadFactory("http-boss-default", true)));
        }
        return bossEventLoopGroup;
    }

    public synchronized static void setWorkerEventLoopGroup(NioEventLoopGroup workerEventLoopGroup) {
        assert HttpUtil.workerEventLoopGroup == null;
        HttpUtil.workerEventLoopGroup = workerEventLoopGroup;
    }

    public synchronized static NioEventLoopGroup getWorkerEventLoopGroup() {
        if (workerEventLoopGroup == null) {
            workerEventLoopGroup = new NioEventLoopGroup(4, Executors.newFixedThreadPool(4, new DefaultThreadFactory("http-worker-default", true)));
        }
        return workerEventLoopGroup;
    }

    public static NioEventLoopGroup getRequestEventLoopGroup() {
        if (requestEventLoopGroup == null) {
            requestEventLoopGroup = new NioEventLoopGroup(2, Executors.newFixedThreadPool(2, new DefaultThreadFactory("http-request-default", true)));
        }
        return requestEventLoopGroup;
    }

    public static void setRequestEventLoopGroup(NioEventLoopGroup requestEventLoopGroup) {
        assert HttpUtil.requestEventLoopGroup == null;
        HttpUtil.requestEventLoopGroup = requestEventLoopGroup;
    }

    public static void initClientPool(String protocol, String host, int port, int size) {
        if (port == -1) {
            port = 80;
        }
        String key = protocol + "://" + host + ":" + port;
        HttpClientPool pool = poolMap.get(key);
        if (pool == null) {
            pool = new HttpClientPool(size, protocol, host, port, getWorkerEventLoopGroup());
            poolMap.put(key, pool);
        }
    }

    public synchronized static HttpClientPool getClientPool(String protocol, String host, int port) {
        if (port == -1) {
            if (protocol.equalsIgnoreCase("https")) {
                port = 443;
            } else {
                port = 80;
            }
        }
        String key = protocol + "://" + host + ":" + port;
        HttpClientPool pool = poolMap.get(key);
        if (pool == null) {
            pool = new HttpClientPool(100, protocol, host, port, getWorkerEventLoopGroup());
            poolMap.put(key, pool);
        }
        return pool;
    }

    public static void getRequest(String path, FutureListener listener) {
        getRequest(path, workerEventLoopGroup, listener);
    }

    public static void getRequest(final String path, final ExecutorService executor, final FutureListener listener) {
        getRequestEventLoopGroup().execute(new Runnable() {
            public void run() {
                try {
                    URL url = new URL(path);
                    HttpClientPool pool = getClientPool(url.getProtocol(), url.getHost(), url.getPort());
                    Request request = new Request(url.getPath() + "?" + url.getQuery(), Request.RequestMethod.GET);
                    RequestFuture future = pool.request(request);
                    future.setService(executor);
                    future.addListener(listener);
                    future.getClient().request();
                } catch (MalformedURLException e) {
                    listener.exception(e);
                }
            }
        });
    }

    public static Response getRequest(String path) {
        try {
            URL url = new URL(path);
            HttpClientPool pool = getClientPool(url.getProtocol(), url.getHost(), url.getPort());
            Request request = new Request(url.getPath() + "?" + url.getQuery(), Request.RequestMethod.GET);
            RequestFuture future = pool.request(request);
            future.getClient().request();
            return future.sync();
        } catch (MalformedURLException e) {
            log.error(e.getMessage(), e);
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static void postRequest(String path, String data, FutureListener listener) {
        postRequest(path, data, workerEventLoopGroup, listener);
    }

    public static void postRequest(final String path, final String data, final ExecutorService executor,
                                   final FutureListener listener) {
        getRequestEventLoopGroup().execute(new Runnable() {
            public void run() {
                try {
                    URL url = new URL(path);
                    HttpClientPool pool = getClientPool(url.getProtocol(), url.getHost(), url.getPort());
                    Request request = new Request(url.getPath() + "?" + url.getQuery(), Request.RequestMethod.POST);
                    if (!StringUtils.isBlank(data)) {
                        request.writeBody(data.getBytes(Charset.forName("utf-8")));
                    }
                    RequestFuture future = pool.request(request);
                    future.setService(executor);
                    future.addListener(listener);
                    future.getClient().request();
                } catch (MalformedURLException e) {
                    listener.exception(e);
                }
            }
        });
    }

    public static Response postRequest(String path, String data) {
        try {
            URL url = new URL(path);
            HttpClientPool pool = getClientPool(url.getProtocol(), url.getHost(), url.getPort());
            Request request = new Request(url.getPath() + "?" + url.getQuery(), Request.RequestMethod.POST);
            if (!StringUtils.isBlank(data)) {
                request.writeBody(data.getBytes(Charset.forName("utf-8")));
            }
            RequestFuture future = pool.request(request);
            future.getClient().request();
            return future.sync();
        } catch (MalformedURLException e) {
            log.error(String.valueOf(e.getStackTrace()));
        } catch (Throwable e) {
            log.error(String.valueOf(e.getStackTrace()));
        }
        return null;
    }

    @Deprecated
    public static String httpRequest(String requestUrl) {
        return httpRequest(requestUrl, "GET", null);
    }

    @Deprecated
    public static String httpRequest(String requestUrl, String requestMethod, String data) {
        StringBuffer buffer = new StringBuffer();
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();

            httpUrlConn.setDoOutput(null != data);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            httpUrlConn.setRequestMethod(requestMethod);
            if (null != data) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                outputStream.write(data.getBytes("UTF-8"));
                outputStream.close();
            }
            httpUrlConn.connect();

            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();

            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();

        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        return buffer.toString();
    }

    @Deprecated
    public static String httpsRequest(String requestUrl) {
        return httpsRequest(requestUrl, "GET", null);
    }

    @Deprecated
    public static String httpsRequest(String requestUrl, String requestMethod, String data) {
        StringBuffer buffer = new StringBuffer();
        try {
            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();

            httpUrlConn.setDoOutput(null != data);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            httpUrlConn.setRequestMethod(requestMethod);
            if (null != data) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                outputStream.write(data.getBytes("UTF-8"));
                outputStream.close();
            }
            httpUrlConn.connect();

            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();

            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();

        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        return buffer.toString();
    }

    public static void httpResponse(ChannelHandlerContext context, HttpRequest request, String response) {
        try {
            httpResponse(context, request, response.getBytes("utf-8"));
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    public static void httpResponse(ChannelHandlerContext context, HttpRequest request, String response,
                                    HttpResponseStatus status) {
        try {
            httpResponse(context, request, response.getBytes("utf-8"), status);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    public static void httpResponse(ChannelHandlerContext context, HttpRequest request, byte[] bytes) {
        httpResponse(context, request, bytes, HttpResponseStatus.OK);
    }

    public static void httpResponse(ChannelHandlerContext context, HttpRequest request, byte[] bytes,
                                    HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status,
                Unpooled.wrappedBuffer(bytes));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=utf-8");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        response.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        if (io.netty.handler.codec.http.HttpUtil.isKeepAlive(request)) {
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            context.write(response);
            context.flush();
        } else {
            context.write(response);
            context.flush();
            context.close();
        }
    }

    public static Map<String, String> parseQuery(String uri, String data) {
        return parseQuery(uri, data, CharsetUtil.UTF_8);
    }

    public static Map<String, String> parseQuery(String uri, String data, Charset charset) {
        Map<String, String> map = new HashMap<String, String>();
        if (!StringUtils.isBlank(uri)) {
            int begin = uri.indexOf("?");
            if (begin != -1) {
                decodeParams(uri, begin + 1, map, charset);
            }
        }
        if (!StringUtils.isBlank(data)) {
            decodeParams(data, 0, map, charset);
        }
        return map;
    }

    public static String appendQuery(String uri, String data) {
        if (StringUtils.isBlank(data)) {
            return uri;
        }
        if (uri.indexOf("?") != -1) {
            if (uri.lastIndexOf("?") == uri.length() - 1) {
                return uri + data;
            }
            return uri + "&" + data;
        } else {
            return uri + "?" + data;
        }
    }

    private static Map<String, String> decodeParams(String s, int from, Map<String, String> params, Charset charset) {
        int len = s.length();
        int nameStart = from;
        int valueStart = -1;
        int i;
        loop:
        for (i = from; i < len; i++) {
            switch (s.charAt(i)) {
                case '=':
                    if (nameStart == i) {
                        nameStart = i + 1;
                    } else if (valueStart < nameStart) {
                        valueStart = i + 1;
                    }
                    break;
                case '&':
                case ';':
                    addParam(s, nameStart, valueStart, i, params, charset);
                    nameStart = i + 1;
                    break;
                case '#':
                    break loop;
                default:
                    // continue
            }
        }
        addParam(s, nameStart, valueStart, i, params, charset);
        return params;
    }

    private static boolean addParam(String s, int nameStart, int valueStart, int valueEnd, Map<String, String> params,
                                    Charset charset) {
        if (nameStart >= valueEnd) {
            return false;
        }
        if (valueStart <= nameStart) {
            valueStart = valueEnd + 1;
        }
        String name = decodeComponent(s, nameStart, valueStart - 1, charset, false);
        String value = decodeComponent(s, valueStart, valueEnd, charset, false);
        params.put(name, value);
        return true;
    }

    private static String decodeComponent(String s, int from, int toExcluded, Charset charset, boolean isPath) {
        int len = toExcluded - from;
        if (len <= 0) {
            return EMPTY_STRING;
        }
        int firstEscaped = -1;
        for (int i = from; i < toExcluded; i++) {
            char c = s.charAt(i);
            if (c == '%' || c == '+' && !isPath) {
                firstEscaped = i;
                break;
            }
        }
        if (firstEscaped == -1) {
            return s.substring(from, toExcluded);
        }
        CharsetDecoder decoder = CharsetUtil.decoder(charset);
        int decodedCapacity = (toExcluded - firstEscaped) / 3;
        ByteBuffer byteBuf = ByteBuffer.allocate(decodedCapacity);
        CharBuffer charBuf = CharBuffer.allocate(decodedCapacity);

        StringBuilder strBuf = new StringBuilder(len);
        strBuf.append(s, from, firstEscaped);

        for (int i = firstEscaped; i < toExcluded; i++) {
            char c = s.charAt(i);
            if (c != '%') {
                strBuf.append(c != '+' || isPath ? c : SPACE);
                continue;
            }

            byteBuf.clear();
            do {
                if (i + 3 > toExcluded) {
                    throw new IllegalArgumentException("unterminated escape sequence at index " + i + " of: " + s);
                }
                byteBuf.put(decodeHexByte(s, i + 1));
                i += 3;
            } while (i < toExcluded && s.charAt(i) == '%');
            i--;

            byteBuf.flip();
            charBuf.clear();
            CoderResult result = decoder.reset().decode(byteBuf, charBuf, true);
            try {
                if (!result.isUnderflow()) {
                    result.throwException();
                }
                result = decoder.flush(charBuf);
                if (!result.isUnderflow()) {
                    result.throwException();
                }
            } catch (CharacterCodingException ex) {
                throw new IllegalStateException(ex);
            }
            strBuf.append(charBuf.flip());
        }
        return strBuf.toString();
    }
}
