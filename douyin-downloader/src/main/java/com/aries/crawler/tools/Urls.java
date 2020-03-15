package com.aries.crawler.tools;

/**
 * @author arowana
 */
public final class Urls {
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

}
