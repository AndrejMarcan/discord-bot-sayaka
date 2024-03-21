/** By YamiY Yaten */
package com.yatensoft.dcbot.persitence.model;

import jakarta.persistence.*;

@Entity
@Table(name = "discord_channel")
public class DiscordChannel {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "discord_channel_name")
    private String discordChannelName;

    @Column(name = "discord_channel_type")
    private String discordChannelType;

    @Column(name = "discord_channel_id")
    private long discordChannelId;

    @Column(name = "discord_server_id")
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

    public String getDiscordChannelType() {
        return discordChannelType;
    }

    public void setDiscordChannelType(String discordChannelType) {
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
