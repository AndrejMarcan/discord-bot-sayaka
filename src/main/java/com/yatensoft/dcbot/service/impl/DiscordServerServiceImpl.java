/** By YamiY Yaten */
package com.yatensoft.dcbot.service.impl;

import com.yatensoft.dcbot.config.CacheConfig;
import com.yatensoft.dcbot.dto.DiscordServerDTO;
import com.yatensoft.dcbot.enumeration.SayakaManagedServerEnum;
import com.yatensoft.dcbot.mapper.BusinessObjectMapper;
import com.yatensoft.dcbot.persitence.model.DiscordServer;
import com.yatensoft.dcbot.persitence.repository.DiscordServerRepository;
import com.yatensoft.dcbot.service.skeleton.DiscordServerService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Implementation of DiscordServerService
 */
@Service
public class DiscordServerServiceImpl implements DiscordServerService {
    private final DiscordServerRepository discordServerRepository;
    private final BusinessObjectMapper businessObjectMapper;

    public DiscordServerServiceImpl(
            @Autowired final DiscordServerRepository discordServerRepository,
            @Autowired final BusinessObjectMapper businessObjectMapper) {
        super();
        this.discordServerRepository = discordServerRepository;
        this.businessObjectMapper = businessObjectMapper;
    }

    /** See {@link DiscordServerService#getDiscordServerByName(SayakaManagedServerEnum)} */
    @Override
    @Cacheable(value = CacheConfig.DISCORD_SERVER_CACHE_NAME, key = "#sayakaManagedServerEnum")
    public DiscordServerDTO getDiscordServerByName(final SayakaManagedServerEnum sayakaManagedServerEnum) {
        final Optional<DiscordServer> discordServerOpt =
                discordServerRepository.findByDiscordServerName(sayakaManagedServerEnum.getServerName());
        return discordServerOpt.isPresent()
                ? businessObjectMapper.mapDiscordServerToDiscordServerDTO(discordServerOpt.get())
                : null;
    }
}
