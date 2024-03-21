/** By YamiY Yaten */
package com.yatensoft.dcbot.persitence.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "discord_server")
public class DiscordServer {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "discord_server_name", length = 64)
    private String discordServerName;

    @Column(name = "discord_server_id")
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
