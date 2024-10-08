/** By YamiY Yaten */
package com.yatensoft.dcbot.plugin.ygo.orchestrator.impl;

import com.yatensoft.dcbot.core.constant.BotCommandConstant;
import com.yatensoft.dcbot.core.constant.MessageConstant;
import com.yatensoft.dcbot.core.orchestrator.skeleton.CommandOrchestrator;
import com.yatensoft.dcbot.plugin.ygo.service.skeleton.YgoCommandService;
import java.io.IOException;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class YgoCommandOrchestrator implements CommandOrchestrator {
    private final YgoCommandService ygoCommandService;

    public YgoCommandOrchestrator(@Autowired final YgoCommandService ygoCommandService) {
        super();
        this.ygoCommandService = ygoCommandService;
    }

    /** See {@link CommandOrchestrator#delegateCommand(String, MessageReceivedEvent)} */
    @Override
    public void delegateCommand(final String command, final MessageReceivedEvent event) throws IOException {
        if (BotCommandConstant.YGO_COMMAND_GET_LATEST_BANLIST_URL.equalsIgnoreCase(command)) {
            ygoCommandService.getLatestBanListUrl(event);
            return;
        }

        /* In case of unknown command send message to the channel where request came from */
        event.getChannel()
                .sendMessage(event.getAuthor().getAsMention() + " " + MessageConstant.UNKNOWN_COMMAND)
                .queue();
    }
}
