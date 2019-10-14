package org.code.common.util.http.proxy;

import java.util.Collection;

/**
 * @author yaotianchi
 * @date 2019/10/14
 */
public abstract class HttpProxyPool {

    protected Collection<HttpProxy> pool;

    /**
     * 初始化Http代理池
     */
    public abstract void initPool();

    /**
     * 从池中获取Http代理
     * @return
     */
    public abstract HttpProxy getHttpProxy();

    /**
     * 往池中添加Http代理
     */
    public abstract void addHttpProxy(HttpProxy httpProxy);



}
