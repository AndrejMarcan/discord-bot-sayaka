/** By YamiY Yaten */
package com.yatensoft.dcbot.listener;

import com.yatensoft.dcbot.constant.MessageConstant;
import com.yatensoft.dcbot.dto.DiscordChannelDTO;
import com.yatensoft.dcbot.dto.DiscordServerDTO;
import com.yatensoft.dcbot.enumeration.KitchenTableTCGsChannelEnum;
import com.yatensoft.dcbot.enumeration.SayakaManagedServerEnum;
import com.yatensoft.dcbot.service.skeleton.DiscordChannelService;
import com.yatensoft.dcbot.service.skeleton.DiscordServerService;
import java.util.Map;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/** Service responsible for executing functions right after bot is initialized */
@Service
public class OnReadyEventListener extends ListenerAdapter {
    @Value("${application.version}")
    private String SAYAKA_VERSION;

    private final DiscordServerService discordServerService;
    private final DiscordChannelService discordChannelService;

    public OnReadyEventListener(
            @Autowired final DiscordServerService discordServerService,
            @Autowired final DiscordChannelService discordChannelService) {
        super();
        this.discordServerService = discordServerService;
        this.discordChannelService = discordChannelService;
    }

    /** ReadyEvent is fired after the bot is fully initialized */
    @Override
    public void onReady(final ReadyEvent event) {
        /* Store discord server and discord server channels into cache with no expiration */
        final DiscordServerDTO discordServerDTO =
                discordServerService.getDiscordServerByName(SayakaManagedServerEnum.KITCHEN_TABLE_TCGS);
        final Map<String, DiscordChannelDTO> kitchenTableTCGsChannels =
                discordChannelService.getDiscordChannelsByDiscordServerId(discordServerDTO.getDiscordServerId());
        final DiscordChannelDTO generalDiscordChannel =
                kitchenTableTCGsChannels.get(KitchenTableTCGsChannelEnum.GENERAL_GENERAL.getChannelKey());

        /* Get channel by ID */
        final TextChannel generalChannel =
                event.getJDA().getTextChannelById(generalDiscordChannel.getDiscordChannelId());
        /* If channel was found send message that bot successfully initialized */
        if (generalChannel != null) {
            generalChannel
                    .sendMessage(String.format(MessageConstant.NEW_VERSION_DEPLOYED_TEMPLATE, SAYAKA_VERSION))
                    .queue();
        }
    }
}
