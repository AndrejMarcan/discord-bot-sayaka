/** By YamiY Yaten */
package com.yatensoft.dcbot.dto;

import java.util.UUID;

public class DiscordServerDTO {
    private UUID id;
    private String discordServerName;
    private long discordServerId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDiscordServerName() {
        return discordServerName;
    }

    public void setDiscordServerName(String discordServerName) {
        this.discordServerName = discordServerName;
    }

    public long getDiscordServerId() {
        return discordServerId;
    }

    public void setDiscordServerId(long discordServerId) {
        this.discordServerId = discordServerId;
    }
}
