package org.code.common.util.http;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class HttpClientUtilTest {

    @Test
    public void test1(){
        HttpResponse response = HttpClientUtil.ISTANCE.request("http://www.baidu.com");
        System.out.println("response = " + response);
    }

    @Test
    public void test2(){
        //HttpResponse response = HttpClientUtil.ISTANCE.request("http://confluence.daojia-inc.com/pages/viewpage.action?pageId=34898683");

    }


    public static void main(String[] args) {

    }

}