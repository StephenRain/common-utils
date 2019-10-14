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
    public void pingUseProxy() {
        boolean pong = HttpHelper.pingUseProxy("http://www.baidu.com", new HttpProxy("47.107.80.182", 8000));
        log.info("pong:" + pong);
    }
}