package com.supconit.mobile.mobileJson.entities;

/**
 * Created by wangwei on 2016-5-24.
 */
public class ImagePath {

    private String path;//图片地址
    private String url;//链接地址
    private String fontColor;//字体颜色
    private String title;//菜单标题

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
