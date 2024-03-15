/** By YamiY Yaten */
package com.yatensoft.dcbot.service.impl;

import com.yatensoft.dcbot.dto.DiscordServerDTO;
import com.yatensoft.dcbot.persitence.repository.DiscordServerRepository;
import com.yatensoft.dcbot.service.skeleton.DiscordServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of DiscordServerService
 */
@Service
public class DiscordServerServiceImpl implements DiscordServerService {

    private final DiscordServerRepository discordServerRepository;

    public DiscordServerServiceImpl(@Autowired final DiscordServerRepository discordServerRepository) {
        super();
        this.discordServerRepository = discordServerRepository;
    }

    /** See {@link DiscordServerService#getDiscordServerByName(java.lang.String)} */
    @Override
    public DiscordServerDTO getDiscordServerByName(final String discordServerName) {
        return null;
    }
}
