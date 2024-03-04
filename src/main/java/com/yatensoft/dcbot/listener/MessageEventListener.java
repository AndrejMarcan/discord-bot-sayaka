/** By YamiY Yaten */
package com.yatensoft.dcbot.listener;

import com.yatensoft.dcbot.constant.MessageConstant;
import com.yatensoft.dcbot.orchestrator.impl.MessageCommandOrchestrator;
import com.yatensoft.dcbot.orchestrator.skeleton.MessageEventOrchestrator;
import com.yatensoft.dcbot.util.BotUtils;
import java.io.IOException;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jsoup.internal.StringUtil;
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
        messageEventOrchestrator.delegateEvent(event);
    }
}
