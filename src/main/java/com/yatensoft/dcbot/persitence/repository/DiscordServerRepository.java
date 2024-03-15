/** By YamiY Yaten */
package com.yatensoft.dcbot.persitence.repository;

import com.yatensoft.dcbot.persitence.model.DiscordServer;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscordServerRepository extends JpaRepository<DiscordServer, UUID> {
    Optional<DiscordServer> findByDiscordServerName(final String discordServerName);
}
