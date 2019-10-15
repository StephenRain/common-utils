package org.code.common.util.http;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.code.common.util.http.proxy.HttpProxy;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @author yaotianchi
 * @date 2019/10/14
 */
@Slf4j
public enum HttpClientUtil {

    ISTANCE;

    private CloseableHttpClient httpClient;

    // 最大连接数
    private static final int MAX_TOTAL = 100;
    // 最大并发数
    private static final int MAX = 20;
    // 失败重试次数
    private static final int RETRY_COUNT = 1;


    RequestConfig.Builder builder = RequestConfig.custom()
            .setConnectTimeout(10000)
            .setConnectionRequestTimeout(10000)
            .setSocketTimeout(10000);

    HttpClientUtil() {
        httpClient = getHttpClient();
    }

    @SneakyThrows
    private static CloseableHttpClient getHttpClient() {
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (x509Certificates, s) -> true).build();
        //带连接池的HttpClients初始化方式
        final SSLConnectionSocketFactory connectionSocketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);

        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", connectionSocketFactory)
                .build();
        final PoolingHttpClientConnectionManager clientConnectionManager = new PoolingHttpClientConnectionManager(registry);
        clientConnectionManager.setMaxTotal(MAX_TOTAL);
        clientConnectionManager.setDefaultMaxPerRoute(MAX);
        return HttpClients.custom()
                .setConnectionManager(clientConnectionManager)
                .setRetryHandler((exception, executionCount, context) -> {
                    boolean retry = (executionCount < RETRY_COUNT);
                    log.info("Http Retry Count:" + executionCount + ",Exception:" + exception.getMessage());
                    return retry;
                }).build();
    }


    /**
     * 使用自定义的请求发起Http请求
     *
     * @param request
     * @return
     */
    public HttpResponse request(HttpRequest request) {
        Objects.requireNonNull(request);
        log.debug("HttpClient Sending:" + request);
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setServerUrl(request.getUrl());
        HttpRequestBase baseRequest = convert(request);
        CloseableHttpResponse response = null;
        try {
            response = execute(baseRequest);
        } catch (IOException e) {
            httpResponse.setE(e);
            return httpResponse;
        }
        // 转换
        Header[] headers = response.getAllHeaders();
        Map<String, String> headerMap = httpResponse.getHeaders();
        for (int i = 0; i < headers.length; i++) {
            if ("Set-Cookie".equals(headers[i].getName())) {
                String value = headers[i].getValue();
                String[] kvs = value.split(";");
                for (int j = 0; j < kvs.length; j++) {
                    String[] kv = kvs[j].split("=");
                    HashMap<String, String> map = new HashMap<>();
                    map.put(kv[0], kv[1]);
                    httpResponse.getCookies().add(map);
                }
            } else {
                headerMap.put(headers[i].getName(), headers[i].getValue());
            }
        }
        HttpEntity responseEntity = response.getEntity();
        Header contentTypeHeader = responseEntity.getContentType();
        if (contentTypeHeader != null) {
            httpResponse.setContentType(contentTypeHeader.getValue());
        }
        try {
            String content = null;
            try {
                content = EntityUtils.toString(responseEntity);
            } catch (Exception e) {
                log.error("responseEntity Error：" + e.getMessage());
            }
            httpResponse.setContent(content);
        } finally {
            try {
                EntityUtils.consume(responseEntity);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return httpResponse;


    }

    public HttpResponse request(String url) {
        return request(new HttpRequest(url));
    }

    /**
     * 使用apache HttpProxy Client 发起请求
     *
     * @param baseRequest
     * @return
     */
    private CloseableHttpResponse execute(HttpRequestBase baseRequest) throws IOException {
        CloseableHttpResponse response = httpClient.execute(baseRequest);
        int status = response.getStatusLine().getStatusCode();
        log.info(baseRequest.getURI() + " Response Code:" + status);
        if (status == 302 || status == 301) {
            log.info("Redirect Loaction:" + response.getFirstHeader("Location").getValue());
        }
        return response;
    }

    /**
     * 转换为真正的请求
     *
     * @param request
     * @return
     */
    private HttpRequestBase convert(HttpRequest request) {
        HttpMethod method = request.getType();
        HttpRequestBase baseRequest = null;
        switch (method) {
            case POST:
                baseRequest = new HttpPost(request.getUrl());
                List<NameValuePair> fields = new ArrayList<>();
                for (Map.Entry<String, String> entry : request.getBody().entrySet()) {
                    NameValuePair nvp = new BasicNameValuePair(entry.getKey(), entry.getValue());
                    fields.add(nvp);
                }
                try {
                    HttpEntity entity = new UrlEncodedFormEntity(fields, "UTF-8");
                    ((HttpEntityEnclosingRequestBase) baseRequest).setEntity(entity);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
            default:
                baseRequest = new HttpGet(request.getUrl());
                break;
        }
        RequestConfig requestConfig = buildRequestConfig(request);
        baseRequest.setConfig(requestConfig);
        request.getHeaders().forEach(baseRequest::addHeader);
        return baseRequest;
    }

    /**
     * 构建Http请求参数
     *
     * @return
     */
    private RequestConfig buildRequestConfig(HttpRequest httpRequest) {
        HttpProxy httpProxy = httpRequest.getHttpProxy();
        if (httpProxy != null) {
            log.info("Use HttpProxy:" + httpProxy);
            builder.setProxy(new HttpHost(httpProxy.getHost(), httpProxy.getPort()));
        }
        return builder.build();
    }
}
