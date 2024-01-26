/** By YamiY Yaten */
package com.yatensoft.dcbot.orchestrator;

import com.yatensoft.dcbot.constant.MessageConstant;
import com.yatensoft.dcbot.constant.OrchestratorConstant;
import com.yatensoft.dcbot.service.impl.fab.FabCommandServiceImpl;
import com.yatensoft.dcbot.service.impl.ygo.YgoCommandServiceImpl;
import com.yatensoft.dcbot.service.skeleton.CommandService;
import java.io.IOException;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageCommandOrchestrator {
    private final CommandService fabCommandService;
    private final CommandService ygoCommandService;

    public MessageCommandOrchestrator(
            @Autowired final FabCommandServiceImpl fabCommandService,
            @Autowired final YgoCommandServiceImpl ygoCommandService) {
        super();
        this.fabCommandService = fabCommandService;
        this.ygoCommandService = ygoCommandService;
    }

    /**
     * Pass the message event and command to the correct service class.
     *
     * @param command command part of the message event
     * @param event message event
     * @throws IOException
     */
    public void delegateCommand(final String command, final MessageReceivedEvent event) throws IOException {
        /* Pass command to the correct service */
        if (command.contains(OrchestratorConstant.FAB_COMMAND_PREFIX)) {
            fabCommandService.handleCommand(command, event);
            return;
        }

        /* In case of unknown command send message to the channel where request came from */
        event.getChannel()
                .sendMessage(event.getAuthor().getAsMention() + " " + MessageConstant.UNKNOWN_COMMAND)
                .queue();
    }
}
