package com.aries.crawler.tools;

/**
 * @author arowana
 */
public final class Urls {
    private static final String CM = "://";
    private static final String USER_SHARE_PAGE_TEMPLATE = "https://www.iesdouyin.com/share/user/%d";

    /**
     * 防止实例化
     */
    private Urls() {

    }

    /**
     * 获取用户分享页的url
     */
    public static String getUserSharePage(final Long uid) {
        return String.format(USER_SHARE_PAGE_TEMPLATE, uid);
    }

    /**
     * @param url http(s)://www.kkk.com/xxx/yyy
     * @return
     */
    public static RequestInfo getInfo(String url) {
        var cmIndex = url.indexOf(CM);
        var hostStart = cmIndex + CM.length();
        var hostEnd = url.indexOf("/", hostStart);
        var host = url.substring(hostStart, hostEnd);//前缀https
        var path = url.substring(hostEnd);
        return new RequestInfo(host, path);
    }

    public static record RequestInfo(String host, String path) {

    }
}
