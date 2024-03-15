/** By YamiY Yaten */
package com.yatensoft.dcbot.service.impl;

import com.yatensoft.dcbot.config.CacheConfig;
import com.yatensoft.dcbot.dto.DiscordChannelDTO;
import com.yatensoft.dcbot.persitence.repository.DiscordChannelRepository;
import com.yatensoft.dcbot.service.skeleton.DiscordChannelService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Implementation of DiscordChannelService
 */
@Service
public class DiscordChannelServiceImpl implements DiscordChannelService {

    private final DiscordChannelRepository discordChannelRepository;

    public DiscordChannelServiceImpl(@Autowired final DiscordChannelRepository discordChannelRepository) {
        super();
        this.discordChannelRepository = discordChannelRepository;
    }

    /** See {@link DiscordChannelService#getDiscordChannelsByDiscordServerId(long)} */
    @Override
    @Cacheable(value = CacheConfig.DISCORD_CHANNEL_CACHE_NAME, key = "#discordServerId")
    public Map<String, DiscordChannelDTO> getDiscordChannelsByDiscordServerId(long discordServerId) {
        return null;
    }
}
