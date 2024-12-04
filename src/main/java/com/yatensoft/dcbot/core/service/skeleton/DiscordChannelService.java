/** By YamiY Yaten */
package com.yatensoft.dcbot.core.service.skeleton;

import com.yatensoft.dcbot.core.dto.DiscordChannelDTO;
import java.util.Map;
import org.springframework.cache.annotation.Cacheable;

/**
 * Service class responsible for handling of database operations related to discord channels
 */
public interface DiscordChannelService {
    /**
     * Map of all discord channel data by discord server ID where key is in format
     * {DISCORD-SERVER}_{DISCORD-CHANNEL-FOLDER}_{discord-channel-name}
     * @param discordServerId discord server id
     * @return map of keys and discord channel data
     */
    @Cacheable
    Map<String, DiscordChannelDTO> getDiscordChannelsByDiscordServerId(final long discordServerId);

    /**
     * Create new discord channel record in DB
     *
     * @param discordChannelDTO discord server DTO
     * @return name of new discord channel
     */
    String createNewDiscordChannel(final DiscordChannelDTO discordChannelDTO);

    /**
     * Delete discord channel record by key
     *
     * !!! This is HARD DELETE !!!
     *
     * @param key internal channel identifier
     * @param discordServerId id of the discord server where the channel is removed
     */
    void deleteDiscordChannelByKey(final String key, final long discordServerId);
}
