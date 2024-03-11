/** By YamiY Yaten */
package com.yatensoft.dcbot.fab.dto;

import java.util.Date;

public class FabPagesListBlockDTO {
    private String articleUrl;
    private String title;
    private Date date;

    // Getters and setters
    public String getLink() {
        return articleUrl;
    }

    public void setLink(String link) {
        this.articleUrl = link;
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
