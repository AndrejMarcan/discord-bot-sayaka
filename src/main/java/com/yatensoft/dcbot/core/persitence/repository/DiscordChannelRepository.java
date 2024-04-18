/** By YamiY Yaten */
package com.yatensoft.dcbot.core.persitence.repository;

import com.yatensoft.dcbot.core.persitence.model.DiscordChannel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscordChannelRepository extends JpaRepository<DiscordChannel, String> {
    List<DiscordChannel> findAllByDiscordServerId(final long discordServerId);
}
