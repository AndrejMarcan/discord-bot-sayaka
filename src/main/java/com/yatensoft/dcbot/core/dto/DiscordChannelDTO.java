/** By YamiY Yaten */
package com.yatensoft.dcbot.core.dto;

import com.yatensoft.dcbot.core.enumeration.DiscordChannelTypeEnum;

public class DiscordChannelDTO {
    private String id;
    private String discordChannelName;
    private DiscordChannelTypeEnum discordChannelType;
    private long discordChannelId;
    private long discordServerId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDiscordChannelName() {
        return discordChannelName;
    }

    public void setDiscordChannelName(String discordChannelName) {
        this.discordChannelName = discordChannelName;
    }

    public DiscordChannelTypeEnum getDiscordChannelType() {
        return discordChannelType;
    }

    public void setDiscordChannelType(DiscordChannelTypeEnum discordChannelType) {
        this.discordChannelType = discordChannelType;
    }

    public long getDiscordChannelId() {
        return discordChannelId;
    }

    public void setDiscordChannelId(long discordChannelId) {
        this.discordChannelId = discordChannelId;
    }

    public long getDiscordServerId() {
        return discordServerId;
    }

    public void setDiscordServerId(long discordServerId) {
        this.discordServerId = discordServerId;
    }
}
