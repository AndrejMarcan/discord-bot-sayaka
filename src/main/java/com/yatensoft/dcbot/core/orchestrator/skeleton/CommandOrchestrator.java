/** By YamiY Yaten */
package com.yatensoft.dcbot.core.orchestrator.skeleton;

import java.io.IOException;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Command Orchestrator interface for command orchestration to correct service/component.
 */
public interface CommandOrchestrator {
    /**
     * Pass the message event and command to the correct service class.
     *
     * @param command command part of the message event
     * @param event message event
     * @throws IOException
     */
    void delegateCommand(final String command, final MessageReceivedEvent event) throws IOException;
}
