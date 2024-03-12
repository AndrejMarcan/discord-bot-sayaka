/** By YamiY Yaten */
package com.yatensoft.dcbot.orchestrator.impl.ygo;

import com.yatensoft.dcbot.constant.BotCommandConstant;
import com.yatensoft.dcbot.constant.MessageConstant;
import com.yatensoft.dcbot.orchestrator.skeleton.CommandOrchestrator;
import com.yatensoft.dcbot.service.skeleton.ygo.YgoCommandService;
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
