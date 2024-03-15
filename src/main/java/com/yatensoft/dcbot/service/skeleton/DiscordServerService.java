/** By YamiY Yaten */
package com.yatensoft.dcbot.service.skeleton;

import com.yatensoft.dcbot.dto.DiscordServerDTO;
import org.springframework.cache.annotation.Cacheable;

/**
 * Service class responsible for handling of database operations related to discord channels
 */
public interface DiscordServerService {
    /**
     * Get discord server data by discord server name
     * @param discordServerName discord server name
     * @return discord server data
     */
    @Cacheable
    DiscordServerDTO getDiscordServerByName(String discordServerName);
}
