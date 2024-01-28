/** By YamiY Yaten */
package com.yatensoft.dcbot.service.impl;

import com.yatensoft.dcbot.service.skeleton.YgoCommandService;
import java.io.IOException;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Service;

/**
 * Implementation class of CommandService interface responsible for handling of commands
 * related to Yu-Gi-Oh! channels.
 */
@Service
public class YgoCommandServiceImpl implements YgoCommandService {
    /** See {@link YgoCommandService#handleCommand(MessageReceivedEvent)} */
    @Override
    public void handleCommand(MessageReceivedEvent event) throws IOException {
        System.out.println("NOT IMPLEMENTED");
    }
}
