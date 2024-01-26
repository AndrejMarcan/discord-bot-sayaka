/** By YamiY Yaten */
package com.yatensoft.dcbot.listener;

import com.yatensoft.dcbot.orchestrator.MessageCommandOrchestrator;
import java.io.IOException;

import com.yatensoft.dcbot.util.BotUtils;
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
        /* Ignore messages where Sayaka(BOT -> @Sayaka) is not mentioned */
        if (!BotUtils.isSayakaMentioned(event.getMessage())) return;
        /* Extract message content */
        final String message = event.getMessage().getContentRaw();
        if (StringUtil.isBlank(message)) {
            return;
        }
        /* Notify users that message was caught */
        event.getChannel().sendTyping().queue();
        /* Pass the message to orchestrator */
        try {
            messageCommandOrchestrator.orchestrateCommand(message, event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
