/** By YamiY Yaten */
package com.yatensoft.dcbot.persitence.repository;

import com.yatensoft.dcbot.persitence.model.DiscordChannel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscordChannelRepository extends JpaRepository<DiscordChannel, String> {
    List<DiscordChannel> findAllByDiscordServerId(final long discordServerId);
}
