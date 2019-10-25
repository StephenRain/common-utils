package org.code.common.util.http;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.code.common.util.http.proxy.HttpProxy;
import org.code.common.util.http.proxy.HttpProxyPool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author yaotianchi
 * @date 2019/10/14
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class HttpRequest extends HttpProxyPool {

    protected String url;

    protected HttpMethod type;

    protected String charset;

    protected HttpProxy[] httpProxies;

    protected Map<String, String> params = new HashMap<>(8);

    protected Map<String, String> headers = new HashMap<>(16);

    protected Map<String, String> body = new HashMap<>(16);

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void addPostField(String key, String value) {
        body.put(key, value);
    }

    HttpRequest() {
    }

    HttpRequest(String url) {
        this.url = url;
        this.type = HttpMethod.GET;
        this.charset = "utf-8";
    }

    HttpRequest(String url, HttpProxy[] httpProxies) {
        this(url);
        this.httpProxies = httpProxies;
        initPool();
    }

    HttpRequest(String url, HttpProxy httpProxy) {
        this(url, new HttpProxy[]{httpProxy});

    }


    @Override
    public void initPool() {
        super.pool = new LinkedBlockingQueue<>();
        for (int i = 0; i < httpProxies.length; i++) {
            addHttpProxy(httpProxies[i]);
        }
    }

    @Override
    public HttpProxy getHttpProxy() {
        if (pool instanceof BlockingQueue) {
            return ((BlockingQueue<HttpProxy>) pool).poll();
        }
        return null;
    }

    @Override
    public void addHttpProxy(HttpProxy httpProxy) {
        if (pool instanceof BlockingQueue) {
            super.pool.add(httpProxy);
        }
    }

    public HttpRequestBuilder build() {
        return new HttpRequestBuilder().build();
    }


    class HttpRequestBuilder {

        private HttpRequest httpRequest;

        /**
         * Get请求
         *
         * @param url
         * @return
         */
        public HttpRequest get(String url) {
            if (httpRequest == null) {
                httpRequest = new HttpRequest();
            }
            httpRequest.setUrl(url);
            httpRequest.setType(HttpMethod.GET);
            return httpRequest;
        }

        private HttpRequestBuilder build(){
            return this;
        }

    }
}
