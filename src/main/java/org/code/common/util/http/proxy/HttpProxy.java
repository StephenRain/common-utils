package org.code.common.util.http.proxy;

import lombok.Data;
import org.code.common.util.http.HttpVerifier;

/**
 * @author yaotianchi
 * @date 2019/10/14
 */
@Data
public class HttpProxy extends HttpVerifier {

    private String host;

    private int port;

    public HttpProxy(String host, int port) {
        this.host = host;
        this.port = port;
    }


    @Override
    public boolean verify() {





        return false;
    }
}
