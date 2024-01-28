/** By YamiY Yaten */
package com.yatensoft.dcbot.listener;

import com.yatensoft.dcbot.constant.MessageConstant;
import com.yatensoft.dcbot.orchestrator.MessageCommandOrchestrator;
import com.yatensoft.dcbot.util.BotUtils;
import java.io.IOException;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jsoup.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Component responsible for handling message events.
 */
@Component
public class MessageEventListener extends ListenerAdapter {
    private final MessageCommandOrchestrator messageCommandOrchestrator;

    public MessageEventListener(@Autowired final MessageCommandOrchestrator messageCommandOrchestrator) {
        super();
        this.messageCommandOrchestrator = messageCommandOrchestrator;
    }

    /** Handle received message events. */
    @Override
    public void onMessageReceived(final MessageReceivedEvent event) {
        /* Ignore BOT messages */
        if (event.getAuthor().isBot()) return;
        /* Extract command from message where Sayaka(BOT -> @Sayaka) is mentioned */
        final String command = extractCommandFromMessageForSayaka(event.getMessage());
        /* Return if message is not for Sayaka or command format is invalid */
        if (StringUtil.isBlank(command)) {
            return;
        }
        /* Notify users that message was caught */
        event.getChannel().sendTyping().queue();
        /* Pass the message to orchestrator */
        try {
            messageCommandOrchestrator.delegateCommand(command, event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /** Extract command from message where Sayaka(BOT -> @Sayaka) was mentioned. */
    private String extractCommandFromMessageForSayaka(final Message message) {
        if (message == null) return null;
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
