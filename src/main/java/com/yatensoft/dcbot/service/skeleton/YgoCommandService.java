/** By YamiY Yaten */
package com.yatensoft.dcbot.service.skeleton;

import java.io.IOException;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Class responsible for handling of Yu-Gi-Oh! related commands.
 */
public interface YgoCommandService {
    /**
     * Handle the command message.
     *
     * @param event message event
     * @throws IOException
     */
    void handleCommand(MessageReceivedEvent event) throws IOException;
}
