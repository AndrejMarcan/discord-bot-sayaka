/** By YamiY Yaten */
package com.yatensoft.dcbot.core.service.impl;

import com.yatensoft.dcbot.core.dto.DiscordChannelDTO;
import com.yatensoft.dcbot.core.dto.DiscordServerDTO;
import com.yatensoft.dcbot.core.enumeration.SayakaManagedServerEnum;
import com.yatensoft.dcbot.core.service.skeleton.DiscordChannelService;
import com.yatensoft.dcbot.core.service.skeleton.DiscordServerService;
import com.yatensoft.dcbot.core.service.skeleton.DiscordService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of DiscordService
 */
@Service
public class DiscordServiceImpl implements DiscordService {
    private final DiscordServerService discordServerService;
    private final DiscordChannelService discordChannelService;

    public DiscordServiceImpl(
            @Autowired final DiscordServerService discordServerService,
            @Autowired final DiscordChannelService discordChannelService) {
        super();
        this.discordServerService = discordServerService;
        this.discordChannelService = discordChannelService;
    }

    /** See {@link DiscordService#getChannelIdByServerAndChannelKey(SayakaManagedServerEnum, String)} */
    @Override
    public long getChannelIdByServerAndChannelKey(
            final SayakaManagedServerEnum sayakaManagedServerEnum, final String discordChannelKey) {
        final DiscordServerDTO discordServerDTO = discordServerService.getDiscordServerByName(sayakaManagedServerEnum);
        final Map<String, DiscordChannelDTO> kitchenTableTCGsChannels =
                discordChannelService.getDiscordChannelsByDiscordServerId(discordServerDTO.getDiscordServerId());
        return kitchenTableTCGsChannels.get(discordChannelKey).getDiscordChannelId();
    }
}
