/** By YamiY Yaten */
package com.yatensoft.dcbot.core.listener;

import com.yatensoft.dcbot.core.config.SayakaConfig;
import com.yatensoft.dcbot.core.constant.DiscordConstant;
import com.yatensoft.dcbot.core.constant.MessageConstant;
import com.yatensoft.dcbot.core.dto.DiscordChannelDTO;
import com.yatensoft.dcbot.core.enumeration.DiscordChannelTypeEnum;
import com.yatensoft.dcbot.core.service.skeleton.DiscordChannelService;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.unions.ChannelUnion;
import net.dv8tion.jda.api.events.channel.ChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.ChannelDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.data.DataObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service responsible for handling discord server events.
 */
@Service
public class DiscordServerEventListener extends ListenerAdapter {

    private final DiscordChannelService discordChannelService;

    public DiscordServerEventListener(@Autowired final DiscordChannelService discordChannelService) {
        super();
        this.discordChannelService = discordChannelService;
    }

    /** Handles events on channel creation */
    @Override
    public void onChannelCreate(final ChannelCreateEvent event) {
        final Guild server = event.getGuild();
        final ChannelUnion channel = event.getChannel();
        final DataObject rawData = event.getRawData();
        final TextChannel defaultChannel = server.getDefaultChannel().asTextChannel();
        /* Send typing */
        defaultChannel.sendTyping();

        /* Save new channel record to DB */
        final String newChannelName =
                discordChannelService.createNewDiscordChannel(buildDiscordChannelDTO(server, channel, rawData));

        /* Send message to the default channel */
        defaultChannel
                .sendMessage(String.format(MessageConstant.NEW_CHANNEL_WAS_CREATED_WITH_NAME_TEMPLATE, newChannelName))
                .queue();
    }

    /** Handles events on channel deletion */
    @Override
    public void onChannelDelete(final ChannelDeleteEvent event) {
        System.out.println(event.getChannel().getName());
    }

    /** Build DiscordChannelDTO */
    private DiscordChannelDTO buildDiscordChannelDTO(
            final Guild server, final ChannelUnion channel, final DataObject rawData) {
        final DiscordChannelDTO discordChannelDTO = new DiscordChannelDTO();

        discordChannelDTO.setId(buildDiscordChannelKey(server, channel, rawData));
        discordChannelDTO.setDiscordChannelName(channel.getName());
        discordChannelDTO.setDiscordChannelId(channel.getIdLong());
        discordChannelDTO.setDiscordChannelType(DiscordChannelTypeEnum.getDiscordChannelTypeFromString(
                channel.getType().name()));
        discordChannelDTO.setDiscordServerId(server.getIdLong());

        return discordChannelDTO;
    }

    /** Build discord channel key value */
    private String buildDiscordChannelKey(final Guild server, final ChannelUnion channel, final DataObject rawData) {
        final String serverNameUpperCase = server.getName().toUpperCase();
        final String channelFolderUpperCase = SayakaConfig.getSayaka()
                .getCategoryById(rawData.getObject(DiscordConstant.RAW_DATA_CHANNEL_EVENT_CONTENT_KEY)
                        .get(DiscordConstant.RAW_DATA_CHANNEL_EVENT_PARENT_ID_KEY)
                        .toString())
                .getName()
                .toUpperCase();
        final String channelName = channel.getName();

        return serverNameUpperCase + "_" + channelFolderUpperCase + "_" + channelName;
    }
}
