/** By YamiY Yaten */
package com.yatensoft.dcbot.orchestrator.impl;

import com.yatensoft.dcbot.constant.BotCommandConstant;
import com.yatensoft.dcbot.constant.MessageConstant;
import com.yatensoft.dcbot.orchestrator.skeleton.CommandOrchestrator;
import java.io.IOException;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implementation of CommandOrchestrator interface for high level orchestration of command to their
 * topic specific orchestrators.
 */
@Component
public class MessageCommandOrchestrator implements CommandOrchestrator {
    private final CommandOrchestrator fabCommandOrchestrator;
    private final CommandOrchestrator ygoCommandOrchestrator;

    public MessageCommandOrchestrator(
            @Autowired final FabCommandOrchestrator fabCommandOrchestrator,
            @Autowired final YgoCommandOrchestrator ygoCommandOrchestrator) {
        super();
        this.fabCommandOrchestrator = fabCommandOrchestrator;
        this.ygoCommandOrchestrator = ygoCommandOrchestrator;
    }

    /** See {@link CommandOrchestrator#delegateCommand(String, MessageReceivedEvent)} */
    public void delegateCommand(final String command, final MessageReceivedEvent event) throws IOException {
        /* Pass command to the correct topic related orchestrator */
        if (command.contains(BotCommandConstant.FAB_COMMAND_PREFIX)) {
            fabCommandOrchestrator.delegateCommand(command, event);
            return;
        }
        if (command.contains(BotCommandConstant.YGO_COMMAND_PREFIX)) {
            ygoCommandOrchestrator.delegateCommand(command, event);
            return;
        }

        /* In case of unknown command send message to the channel where request came from */
        event.getChannel()
                .sendMessage(event.getAuthor().getAsMention() + " " + MessageConstant.UNKNOWN_COMMAND)
                .queue();
    }
}
