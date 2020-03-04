package com.aries.crawler.tools;

/**
 * @author arowana
 */
public final class Urls {
    /**
     * 防止实例化
     */
    private Urls() {

    }

    private static final String USER_SHARE_PAGE_TEMPLATE = "https://www.iesdouyin.com/share/user/%d";

    /**
     * 获取用户分享页的url
     */
    public static String getUserSharePage(final long uid) {
        return String.format(USER_SHARE_PAGE_TEMPLATE, uid);
    }

}
