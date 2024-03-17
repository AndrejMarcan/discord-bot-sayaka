/** By YamiY Yaten */
package com.yatensoft.dcbot.service.impl;

import com.yatensoft.dcbot.config.CacheConfig;
import com.yatensoft.dcbot.dto.DiscordChannelDTO;
import com.yatensoft.dcbot.mapper.BusinessObjectMapper;
import com.yatensoft.dcbot.persitence.model.DiscordChannel;
import com.yatensoft.dcbot.persitence.repository.DiscordChannelRepository;
import com.yatensoft.dcbot.service.skeleton.DiscordChannelService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * Implementation of DiscordChannelService
 */
@Service
public class DiscordChannelServiceImpl implements DiscordChannelService {
    private final DiscordChannelRepository discordChannelRepository;
    private final BusinessObjectMapper businessObjectMapper;

    public DiscordChannelServiceImpl(
            @Autowired final DiscordChannelRepository discordChannelRepository,
            @Autowired final BusinessObjectMapper businessObjectMapper) {
        super();
        this.discordChannelRepository = discordChannelRepository;
        this.businessObjectMapper = businessObjectMapper;
    }

    /** See {@link DiscordChannelService#getDiscordChannelsByDiscordServerId(long)} */
    @Override
    @Cacheable(value = CacheConfig.DISCORD_CHANNEL_CACHE_NAME, key = "#discordServerId")
    public Map<String, DiscordChannelDTO> getDiscordChannelsByDiscordServerId(final long discordServerId) {
        final List<DiscordChannel> discordChannels = discordChannelRepository.findAllByDiscordServerId(discordServerId);
        return getMapOfDiscordChannels(discordChannels);
    }

    /** Get map of discord channels from the list of discord channels */
    private Map<String, DiscordChannelDTO> getMapOfDiscordChannels(final List<DiscordChannel> discordChannels) {
        if (CollectionUtils.isEmpty(discordChannels)) {
            return new HashMap<>();
        }
        return discordChannels.stream()
                .map(discordChannel -> businessObjectMapper.mapDiscordChannelToDiscordChannelDTO(discordChannel))
                .collect(Collectors.toMap(discordChannel -> discordChannel.getId(), discordChannel -> discordChannel));
    }
}
