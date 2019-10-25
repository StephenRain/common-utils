package org.code.common.util.http;

import lombok.Data;

import java.util.*;

/**
 * @author yaotianchi
 * @date 2019/8/19
 */
@Data
public class HttpResponse extends HttpVerifier {

    private String content;

    private String contentType;

    private String charset;

    private int status;

    private Exception e;

    /**
     * 表示请求那个URL响应的该结果
     */
    private String serverUrl;

    private Map<String, String> headers = new HashMap<>();

    private List<Map<String, String>> cookies = new ArrayList<>();


    /**
     * 通过当前对象中构建一个cookie出来，如果没有cookie则使用默认传入的值
     *
     * @param defaultCookie
     * @return
     */
    public String genCookie(String defaultCookie) {
        StringBuilder sb = new StringBuilder(defaultCookie);
        List<Map<String, String>> cookies = this.getCookies();
        if (cookies.isEmpty()) {
            return defaultCookie;
        }
        cookies.stream().forEach((e) -> {
            Set<String> keySet = e.keySet();
            for (String key : keySet) {
                sb.append(key).append("=").append(e.get(key)).append(";");
            }
        });
        return sb.toString();
    }

    /**
     * 校验HttpResonse是都有效
     *
     * @return
     */
    @Override
    public boolean verify() {
        // todo
        return e == null;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "content='" + content + '\'' +
                ", contentType='" + contentType + '\'' +
                ", charset='" + charset + '\'' +
                ", status=" + status +
                ", e=" + e +
                ", serverUrl='" + serverUrl + '\'' +
                ", headers=" + headers +
                '}';
    }
}
