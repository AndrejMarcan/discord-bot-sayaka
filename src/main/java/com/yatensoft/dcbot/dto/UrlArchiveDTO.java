/** By YamiY Yaten */
package com.yatensoft.dcbot.dto;

import com.yatensoft.dcbot.enumeration.ArchiveTypeEnum;
import com.yatensoft.dcbot.enumeration.TopicEnum;
import java.util.Date;
import java.util.UUID;

public class UrlArchiveDTO {
    private UUID id;
    private String url;
    private TopicEnum topic;
    private ArchiveTypeEnum type;
    private Date dateOfCreation;

    public UrlArchiveDTO() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public TopicEnum getTopic() {
        return topic;
    }

    public void setTopic(TopicEnum topic) {
        this.topic = topic;
    }

    public ArchiveTypeEnum getType() {
        return type;
    }

    public void setType(ArchiveTypeEnum type) {
        this.type = type;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public static class Builder {
        private final UrlArchiveDTO instance = new UrlArchiveDTO();

        public Builder id(UUID id) {
            instance.id = id;
            return this;
        }

        public Builder url(String url) {
            instance.url = url;
            return this;
        }

        public Builder topic(TopicEnum topic) {
            instance.topic = topic;
            return this;
        }

        public Builder type(ArchiveTypeEnum type) {
            instance.type = type;
            return this;
        }

        public Builder dateOfCreation(Date dateOfCreation) {
            instance.dateOfCreation = dateOfCreation;
            return this;
        }

        public UrlArchiveDTO build() {
            return instance;
        }
    }
}
