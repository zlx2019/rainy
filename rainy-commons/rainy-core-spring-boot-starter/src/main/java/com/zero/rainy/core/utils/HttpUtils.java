package com.zero.rainy.core.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * HTTP 客户端工具
 *
 * @author Zero.
 * <p> Created on 2024/8/13 11:43 </p>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpUtils {

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);
    private static final Map<String,String> DEFAULT_HEADER = new HashMap<>(4);

    static {
        DEFAULT_HEADER.put(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        DEFAULT_HEADER.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    }

    private static final HttpClient CLIENT = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            // 重定向策略
            .followRedirects(HttpClient.Redirect.NORMAL)
            // 全局请求超时时长
            .connectTimeout(Duration.ofSeconds(3))
            // 设置线程池
            // .executor()
            // 设置代理服务器(如有需要)
            // .proxy(ProxySelector.of(new InetSocketAddress("127.0.0.1", 7890)))
            // 认证方式
            // .authenticator(Authenticator.getDefault())
            .build();


    /**
     * 发送 HTTP GET 请求
     *
     * @param url     请求地址
     * @param headers 请求头
     * @param timeout 请求超时时间
     */
    private static <R> R getRequest(String url, Map<String, String> headers, Duration timeout, TypeReference<R> type, Class<R> clazz) {
        return sendRequest2Json("GET", url, null, headers, timeout, type, clazz);
    }
    public static <R> R get2Json(String url, Map<String, String> headers, TypeReference<R> type) {
        return getRequest(url, headers, DEFAULT_TIMEOUT, type, null);
    }
    public static <R> R get2Json(String url, Map<String, String> headers, Class<R> clazz) {
        return getRequest(url, headers, DEFAULT_TIMEOUT, null, clazz);
    }
    public static <R> R get2Json(String url, TypeReference<R> type) {
        return getRequest(url, DEFAULT_HEADER, DEFAULT_TIMEOUT, type, null);
    }
    public static <R> R get2Json(String url, Class<R> clazz) {
        return getRequest(url, DEFAULT_HEADER, DEFAULT_TIMEOUT, null, clazz);
    }
    public static String get(String url) {
        return get(url, DEFAULT_HEADER, DEFAULT_TIMEOUT);
    }
    public static String get(String url, Map<String, String> headers) {
        return get(url, headers, DEFAULT_TIMEOUT);
    }
    public static String get(String url, Map<String, String> headers, Duration timeout) {
        return sendRequest("GET", url, null, headers, timeout).body();
    }

    /**
     * 发送 HTTP Post 请求
     *
     * @param url     请求地址
     * @param body    请求体
     * @param headers 请求头
     * @param timeout 请求超时时间
     */
    private static <T,R> R postRequest(String url, T body, Map<String, String> headers, Duration timeout, TypeReference<R> type, Class<R> clazz) {
        return sendRequest2Json("POST", url, body, headers, timeout, type, clazz);
    }
    public static <T> HttpResponse<String> post(String url, T body) {
        return post(url, body, DEFAULT_HEADER);
    }
    public static <T> HttpResponse<String> post(String url, T body, Map<String, String> headers) {
        return sendRequest("POST", url, JsonUtils.toJson(body), headers, DEFAULT_TIMEOUT);
    }
    public static HttpResponse<String> post(String url, String body, Map<String, String> headers) {
        return sendRequest("POST", url, body, headers, DEFAULT_TIMEOUT);
    }
    public static <T> String post2String(String url, T body) {
        return post2String(url, body, DEFAULT_HEADER);
    }
    public static <T> String post2String(String url, T body, Map<String, String> headers) {
        return sendRequest("POST", url, JsonUtils.toJson(body), headers, DEFAULT_TIMEOUT).body();
    }
    public static <T,R> R post2Json(String url, T body, Class<R> clazz) {
        return postRequest(url, body, DEFAULT_HEADER, DEFAULT_TIMEOUT, null, clazz);
    }
    public static <T,R> R post2Json(String url, T body, Map<String, String> headers, TypeReference<R> type)  {
        return postRequest(url, body, headers, DEFAULT_TIMEOUT, type, null);
    }
    public static <T,R> R post2Json(String url, T body, TypeReference<R> type) {
        return postRequest(url, body, DEFAULT_HEADER, DEFAULT_TIMEOUT, type, null);
    }



    /**
     * 发送同步 HTTP 请求，将响应数据反序列化为实体
     *
     * @param method  请求类型
     * @param url     请求地址
     * @param body    请求体
     * @param headers 请求头
     * @param timeout 请求超时时间
     */
    public static <T,R> R sendRequest2Json(String method, String url, T body, Map<String, String> headers, Duration timeout, TypeReference<R> type, Class<R> clazz) {
        if (Objects.nonNull(clazz)){
            return sendRequest2Json(method, url, JsonUtils.toJson(body), headers, timeout, clazz);
        }else if (Objects.nonNull(type)){
            return sendRequest2Json(method, url, JsonUtils.toJson(body), headers, timeout, type);
        }
        return null;
    }

    private static <R> R sendRequest2Json(String method, String url, String body, Map<String, String> headers, Duration timeout, Class<R> clazz) {
        HttpResponse<String> response = sendRequest(method, url, body, headers, timeout);
        return JsonUtils.toObj(response.body(), clazz);
    }
    private static <R> R sendRequest2Json(String method, String url, String body, Map<String, String> headers, Duration timeout, TypeReference<R> type) {
        HttpRequest request = buildRequest(method, url, body, headers, timeout);
        try {
            return CLIENT.send(request, new JsonEntityType<R>(type)).body();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 发送同步 HTTP 请求，返回响应体
     * @param method  请求类型
     * @param url     请求地址
     * @param body    请求体
     * @param headers 请求头
     * @param timeout 请求超时时间
     */
    private static HttpResponse<String> sendRequest(String method, String url, String body, Map<String, String> headers, Duration timeout) {
        HttpRequest request = buildRequest(method, url, body, headers, timeout);
        try {
            return CLIENT.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 构建 HTTP Request
     * @param method  请求类型
     * @param url     请求地址
     * @param body    请求体
     * @param headers 请求头
     * @param timeout 请求超时时间
     * @return         HTTP Request
     */
    private static HttpRequest buildRequest(String method, String url, String body, Map<String, String> headers, Duration timeout){
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .headers(buildHeaders(headers))
                .timeout(timeout);
        if (StringUtils.isNotBlank(body)){
            builder.method(method, HttpRequest.BodyPublishers.ofString(body));
        }else {
            builder.method(method, HttpRequest.BodyPublishers.noBody());
        }
        return builder.build();
    }

    /**
     * 构建请求头
     */
    private static String[] buildHeaders(Map<String, String> headerMap) {
        if (headerMap == null || headerMap.isEmpty()) {
            headerMap = DEFAULT_HEADER;
        }
        String[] headers = new String[headerMap.size() * 2];
        int n = 0;
        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            headers[n++] = entry.getKey();
            headers[n++] = entry.getValue();
        }
        return headers;
    }

    /**
     * HTTP 响应体自动转换
     * @param <T>
     */
    public static class JsonEntityType<T> implements HttpResponse.BodyHandler<T> {
        private final TypeReference<T> typeReference;
        public JsonEntityType(TypeReference<T> type) {
            this.typeReference = type;
        }
        @Override
        public HttpResponse.BodySubscriber<T> apply(HttpResponse.ResponseInfo responseInfo) {
            return HttpResponse.BodySubscribers.mapping(
                    HttpResponse.BodySubscribers.ofString(StandardCharsets.UTF_8),
                    (String body) -> {
                        try {
                            return JsonUtils.toObj(body, typeReference);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
            );
        }
    }
}
