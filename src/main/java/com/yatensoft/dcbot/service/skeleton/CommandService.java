/** By YamiY Yaten */
package com.yatensoft.dcbot.service.skeleton;

import java.io.IOException;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Class responsible for handling of commands.
 */
public interface CommandService {
    /**
     * Handle the command message.
     *
     * @param command command
     * @param event message event
     * @throws IOException
     */
    void handleCommand(String command, MessageReceivedEvent event) throws IOException;
}
