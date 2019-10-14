package org.code.common.util.http;

import org.junit.Test;

public class HttpClientUtilTest {

    @Test
    public void test1(){
        HttpResponse response = HttpClientUtil.ISTANCE.request("http://www.baidu.com");
        System.out.println("response = " + response);
    }


    public static void main(String[] args) {

    }

}