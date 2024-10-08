/** By YamiY Yaten */
package com.yatensoft.dcbot.plugin.fab.dto;

import java.util.Date;

public class FabArticleDTO {
    private String url;
    private String title;
    private Date date;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
