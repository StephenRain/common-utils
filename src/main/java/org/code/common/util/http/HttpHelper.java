package org.code.common.util.http;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.code.common.util.http.proxy.HttpProxy;

import java.util.concurrent.TimeUnit;

/**
 * @author yaotianchi
 * @date 2019/10/14
 */
@Slf4j
public class HttpHelper {

    /**
     * 测试连通性
     *
     * @return
     */
    public static boolean ping(String targeURL) {
        HttpResponse response = HttpClientUtil.ISTANCE.request(targeURL);
        return response != null && response.verify();
    }

    /**
     * 测试代理可用性
     */
    @SneakyThrows
    private static void pingUseProxy(String targetURL, HttpProxy httpProxy) {
        HttpRequest request = new HttpRequest(targetURL, httpProxy);
        while (true) {
            HttpResponse response = HttpClientUtil.ISTANCE.request(request);
            if (response == null || !response.verify()) {
                log.info("FAIL");
            }
            log.info("PONG");
            TimeUnit.SECONDS.sleep(2);
        }
    }

    /**
     * @param targetURL
     * @param ipPort    12.32.33.44:8080 这种格式
     * @return
     */
    public static void pingUseProxy(String targetURL, String ipPort) {
        String[] iport = ipPort.split(":");
        new Thread(() -> pingUseProxy(targetURL, new HttpProxy(iport[0], Integer.parseInt(iport[1])))).start();
        try {
            TimeUnit.MINUTES.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
