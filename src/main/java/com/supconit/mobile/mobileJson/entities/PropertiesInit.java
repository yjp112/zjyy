package com.supconit.mobile.mobileJson.entities;

/**
 * Created by wangwei on 2016-5-12.
 */
public class PropertiesInit {

    /**主页前是否需要登录 **/
    private Boolean needLoginBeforeIndex;

    /**手势密码背景地址 **/
    private String setKeyPageBackground;

    /**根目录菜单版本 **/
    private String rootMenusVersion;

    /**首页滑动版本号 **/
    private String indexSlideVersion;

    /**引导页版本号 **/
    private String guidePageVersion;

    /**登录背景地址 **/
    private String loginPageBackground;

    /**底部菜单版本 **/
    private String footMenusVersion;

    /** **/
    private String headerFontColor;
    private String backIcon;//返回图标的url
    private int styleIndex;//当前主页采取样式编号
    private String headerBg;//头部颜色
    private String indexUrl;//首页地址
    private boolean isGuidePages;//是否开启引导页
    private String cover;//封面地址
    private String iosVersion;//IOS版本号
    private String androidVersion;//安卓版本号
    private String serverVersion;//服务器版本号

    public Boolean getNeedLoginBeforeIndex() {
        return needLoginBeforeIndex;
    }

    public void setNeedLoginBeforeIndex(Boolean needLoginBeforeIndex) {
        this.needLoginBeforeIndex = needLoginBeforeIndex;
    }

    public String getSetKeyPageBackground() {
        return setKeyPageBackground;
    }

    public void setSetKeyPageBackground(String setKeyPageBackground) {
        this.setKeyPageBackground = setKeyPageBackground;
    }

    public String getRootMenusVersion() {
        return rootMenusVersion;
    }

    public void setRootMenusVersion(String rootMenusVersion) {
        this.rootMenusVersion = rootMenusVersion;
    }

    public String getIndexSlideVersion() {
        return indexSlideVersion;
    }

    public void setIndexSlideVersion(String indexSlideVersion) {
        this.indexSlideVersion = indexSlideVersion;
    }

    public String getGuidePageVersion() {
        return guidePageVersion;
    }

    public void setGuidePageVersion(String guidePageVersion) {
        this.guidePageVersion = guidePageVersion;
    }

    public String getLoginPageBackground() {
        return loginPageBackground;
    }

    public void setLoginPageBackground(String loginPageBackground) {
        this.loginPageBackground = loginPageBackground;
    }

    public String getFootMenusVersion() {
        return footMenusVersion;
    }

    public void setFootMenusVersion(String footMenusVersion) {
        this.footMenusVersion = footMenusVersion;
    }

    public String getHeaderFontColor() {
        return headerFontColor;
    }

    public void setHeaderFontColor(String headerFontColor) {
        this.headerFontColor = headerFontColor;
    }

    public String getBackIcon() {
        return backIcon;
    }

    public void setBackIcon(String backIcon) {
        this.backIcon = backIcon;
    }

    public int getStyleIndex() {
        return styleIndex;
    }

    public void setStyleIndex(int styleIndex) {
        this.styleIndex = styleIndex;
    }

    public String getHeaderBg() {
        return headerBg;
    }

    public void setHeaderBg(String headerBg) {
        this.headerBg = headerBg;
    }

    public String getIndexUrl() {
        return indexUrl;
    }

    public void setIndexUrl(String indexUrl) {
        this.indexUrl = indexUrl;
    }

    public boolean getIsGuidePages() {
        return isGuidePages;
    }

    public void setIsGuidePages(boolean isGuidePages) {
        this.isGuidePages = isGuidePages;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getIosVersion() {
        return iosVersion;
    }

    public void setIosVersion(String iosVersion) {
        this.iosVersion = iosVersion;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public String getServerVersion() {
        return serverVersion;
    }

    public void setServerVersion(String serverVersion) {
        this.serverVersion = serverVersion;
    }
}
