package com.yatensoft.dcbot.orchestrator.impl;

import com.yatensoft.dcbot.constant.ChannelConstant;
import com.yatensoft.dcbot.constant.MessageConstant;
import com.yatensoft.dcbot.orchestrator.skeleton.MessageEventOrchestrator;
import com.yatensoft.dcbot.util.BotUtils;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jsoup.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;

@Component
public class MessageEventOrchestratorImpl implements MessageEventOrchestrator {
    private final MessageCommandOrchestrator messageCommandOrchestrator;

    public MessageEventOrchestratorImpl(@Autowired final MessageCommandOrchestrator messageCommandOrchestrator) {
        super();
        this.messageCommandOrchestrator = messageCommandOrchestrator;
    }

    @Override
    public void delegateEvent(final MessageReceivedEvent event) {
        /* Extract command from message where Sayaka(BOT -> @Sayaka) is mentioned */
        final String command = extractCommandFromMessageForSayaka(event.getMessage());
        if(isMessageFromMoviesAndSeriesChannel(event)){
            if(isCommandEvent(command)) {
                handleCommandMessageEvent(command, event);
            }

        }
        //handle command message event
        if(isCommandEvent(command)) {
            handleCommandMessageEvent(command, event);
        }
    }

    private boolean isMessageFromMoviesAndSeriesChannel(final MessageReceivedEvent event) {
        long eventChannelId = event.getChannel().getIdLong();
        return ChannelConstant.MOVIES_AND_SERIES_CHANNEL == eventChannelId;
    }

    private void handleCommandMessageEvent(final String command, final MessageReceivedEvent event) {
        /* Notify users that command message was caught */
        event.getChannel().sendTyping().queue();
        /* Pass the message to command orchestrator */
        try {
            messageCommandOrchestrator.delegateCommand(command, event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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
