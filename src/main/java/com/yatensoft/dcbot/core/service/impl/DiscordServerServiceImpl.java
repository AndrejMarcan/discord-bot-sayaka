/** By YamiY Yaten */
package com.yatensoft.dcbot.core.service.impl;

import com.yatensoft.dcbot.core.config.CacheConfig;
import com.yatensoft.dcbot.core.dto.DiscordServerDTO;
import com.yatensoft.dcbot.core.enumeration.SayakaManagedServerEnum;
import com.yatensoft.dcbot.core.mapper.BusinessObjectMapper;
import com.yatensoft.dcbot.core.persitence.model.DiscordServer;
import com.yatensoft.dcbot.core.persitence.repository.DiscordServerRepository;
import com.yatensoft.dcbot.core.service.skeleton.DiscordServerService;
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
