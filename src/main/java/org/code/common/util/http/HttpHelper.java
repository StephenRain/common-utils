package org.code.common.util.http;

import lombok.extern.slf4j.Slf4j;
import org.code.common.util.http.proxy.HttpProxy;

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
    public static boolean pingUseProxy(String targetURL, HttpProxy httpProxy){
        HttpRequest request = new HttpRequest(targetURL,httpProxy);
        HttpResponse response = HttpClientUtil.ISTANCE.request(request);
        if(response == null || !response.verify()){
            return false;
        }
        return true;
    }


}
