package org.code.common.util.http;

import lombok.extern.slf4j.Slf4j;
import org.code.common.util.http.proxy.HttpProxy;
import org.junit.Test;

@Slf4j
public class HttpHelperTest {

    @Test
    public void ping() {

        boolean pong = HttpHelper.ping("http://www.baidu.com");
        log.info("pong:" + pong);
    }

    @Test
    public void pingUseProxy() throws InterruptedException {
       HttpHelper.pingUseProxy("http://www.baidu.com", "122.51.51.6:1800");
    }
}