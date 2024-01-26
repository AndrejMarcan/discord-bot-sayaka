/** By YamiY Yaten */
package com.yatensoft.dcbot.service.impl.ygo;

import com.yatensoft.dcbot.service.impl.fab.FabWebsiteParser;
import com.yatensoft.dcbot.service.skeleton.CommandService;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation class of CommandService interface responsible for handling of commands
 * related to Yu-Gi-Oh! channels.
 */
@Service
public class YgoCommandServiceImpl implements CommandService {
    private final FabWebsiteParser parser;

    public YgoCommandServiceImpl(@Autowired final FabWebsiteParser parser) {
        super();
        this.parser = parser;
    }

    @Override
    /** See {@link CommandService#handleCommand(String, MessageReceivedEvent)} */
    public void handleCommand(final String command, final MessageReceivedEvent event) {}
}
