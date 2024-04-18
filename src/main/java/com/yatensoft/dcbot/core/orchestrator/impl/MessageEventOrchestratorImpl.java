/** By YamiY Yaten */
package com.yatensoft.dcbot.core.orchestrator.impl;

import com.yatensoft.dcbot.core.constant.MessageConstant;
import com.yatensoft.dcbot.core.enumeration.KitchenTableTCGsChannelEnum;
import com.yatensoft.dcbot.core.enumeration.SayakaManagedServerEnum;
import com.yatensoft.dcbot.core.orchestrator.skeleton.MessageEventOrchestrator;
import com.yatensoft.dcbot.core.service.skeleton.DiscordService;
import com.yatensoft.dcbot.core.service.skeleton.MoviesAndSeriesChannelService;
import com.yatensoft.dcbot.core.service.skeleton.MusicChannelService;
import com.yatensoft.dcbot.core.util.BotUtils;
import java.io.IOException;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jsoup.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implementation of MessageEventOrchestrator interface responsible for delegating message event to their respective handlers.
 */
@Component
public class MessageEventOrchestratorImpl implements MessageEventOrchestrator {
    private final MessageCommandOrchestrator messageCommandOrchestrator;
    private final MoviesAndSeriesChannelService moviesAndSeriesChannelService;
    private final MusicChannelService musicChannelService;
    private final DiscordService discordService;

    public MessageEventOrchestratorImpl(
            @Autowired final MessageCommandOrchestrator messageCommandOrchestrator,
            @Autowired final MoviesAndSeriesChannelService moviesAndSeriesChannelService,
            @Autowired final MusicChannelService musicChannelService,
            @Autowired final DiscordService discordService) {
        super();
        this.messageCommandOrchestrator = messageCommandOrchestrator;
        this.moviesAndSeriesChannelService = moviesAndSeriesChannelService;
        this.musicChannelService = musicChannelService;
        this.discordService = discordService;
    }

    /** See {@link MessageEventOrchestrator#handleEvent(MessageReceivedEvent)} */
    @Override
    public void handleEvent(final MessageReceivedEvent event) {
        /* Extract command from message where Sayaka(BOT -> @Sayaka) is mentioned */
        final String command = extractCommandFromMessageForSayaka(event.getMessage());
        /* Handle command message - should be handled in all channels */
        if (isCommandEvent(command)) {
            /* Notify users that command message was caught */
            event.getChannel().sendTyping().queue();
            /* Pass the message to command orchestrator */
            try {
                messageCommandOrchestrator.delegateCommand(command, event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        /* Process message in channel specific way */
        if (isMessageFromMoviesAndSeriesChannel(event)) {
            moviesAndSeriesChannelService.handleMessageEvent(event);
            return;
        }
        /* Process message in channel specific way */
        if (isMessageFromMusicChannel(event)) {
            musicChannelService.handleMessageEvent(event);
            return;
        }
    }

    /** Validate if message event is from movies-and-series channel */
    private boolean isMessageFromMoviesAndSeriesChannel(final MessageReceivedEvent event) {
        return discordService.getChannelIdByServerAndChannelKey(
                        SayakaManagedServerEnum.KITCHEN_TABLE_TCGS,
                        KitchenTableTCGsChannelEnum.ENTERTAINMENT_MOVIES_AND_SERIES.getChannelKey())
                == event.getChannel().getIdLong();
    }

    /** Validate if message event is from music channel */
    private boolean isMessageFromMusicChannel(final MessageReceivedEvent event) {
        return discordService.getChannelIdByServerAndChannelKey(
                        SayakaManagedServerEnum.KITCHEN_TABLE_TCGS,
                        KitchenTableTCGsChannelEnum.ENTERTAINMENT_MUSIC.getChannelKey())
                == event.getChannel().getIdLong();
    }

    /** Validate if message event contains any command */
    private boolean isCommandEvent(final String command) {
        return !StringUtil.isBlank(command);
    }

    /** Extract command from message where Sayaka(BOT -> @Sayaka) was mentioned. */
    private String extractCommandFromMessageForSayaka(final Message message) {
        if (message == null) {
            return null;
        }
        final String command = BotUtils.isSayakaMentioned(message) ? parseMessageContent(message) : null;
        return BotUtils.hasValidCommandFormat(command) ? command : null;
    }

    /** Parse message raw content - split on bot mention get next word. */
    private String parseMessageContent(final Message message) {
        return message.getContentRaw()
                .split(BotUtils.getSayakaAsMentionInRawFormat())[1]
                .trim()
                .split(MessageConstant.REGEX_TO_SPLIT_MESSAGE_ON_WHITESPACE_CHARACTERS)[0];
    }
}
