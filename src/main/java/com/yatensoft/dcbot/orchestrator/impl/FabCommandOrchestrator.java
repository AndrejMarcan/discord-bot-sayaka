/** By YamiY Yaten */
package com.yatensoft.dcbot.orchestrator.impl;

import com.yatensoft.dcbot.constant.BotCommandConstant;
import com.yatensoft.dcbot.constant.MessageConstant;
import com.yatensoft.dcbot.orchestrator.skeleton.CommandOrchestrator;
import com.yatensoft.dcbot.service.skeleton.FabCommandService;
import java.io.IOException;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FabCommandOrchestrator implements CommandOrchestrator {

    private final FabCommandService fabCommandService;

    public FabCommandOrchestrator(@Autowired final FabCommandService fabCommandService) {
        super();
        this.fabCommandService = fabCommandService;
    }

    /** See {@link CommandOrchestrator#delegateCommand(String, MessageReceivedEvent)} */
    @Override
    public void delegateCommand(final String command, final MessageReceivedEvent event) throws IOException {
        if (BotCommandConstant.FAB_COMMAND_GET_LATEST_ARTICLE_URL.equalsIgnoreCase(command)) {
            fabCommandService.getLatestArticleURL(event);
            return;
        }

        /* In case of unknown command send message to the channel where request came from */
        event.getChannel()
                .sendMessage(event.getAuthor().getAsMention() + " " + MessageConstant.UNKNOWN_COMMAND)
                .queue();
    }
}
