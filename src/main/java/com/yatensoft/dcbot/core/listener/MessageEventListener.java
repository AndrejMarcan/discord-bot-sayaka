/** By YamiY Yaten */
package com.yatensoft.dcbot.core.listener;

import com.yatensoft.dcbot.core.orchestrator.skeleton.MessageEventOrchestrator;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service responsible for handling message events.
 */
@Service
public class MessageEventListener extends ListenerAdapter {
    private final MessageEventOrchestrator messageEventOrchestrator;

    public MessageEventListener(@Autowired final MessageEventOrchestrator messageEventOrchestrator) {
        super();
        this.messageEventOrchestrator = messageEventOrchestrator;
    }

    /** Handle received message events. */
    @Override
    public void onMessageReceived(final MessageReceivedEvent event) {
        /* Ignore BOT messages */
        if (event.getAuthor().isBot()) {
            return;
        }
        /* Pass the message to orchestrator */
        messageEventOrchestrator.handleEvent(event);
    }
}
