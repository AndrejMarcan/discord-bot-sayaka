/** By YamiY Yaten */
package com.yatensoft.dcbot.orchestrator.impl;

import com.yatensoft.dcbot.constant.MessageConstant;
import com.yatensoft.dcbot.orchestrator.skeleton.CommandOrchestrator;
import java.io.IOException;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

@Component
public class YgoCommandOrchestrator implements CommandOrchestrator {
    @Override
    public void delegateCommand(String command, MessageReceivedEvent event) throws IOException {
        /* In case of unknown command send message to the channel where request came from */
        event.getChannel()
                .sendMessage(event.getAuthor().getAsMention() + " " + MessageConstant.UNKNOWN_COMMAND)
                .queue();
    }
}
