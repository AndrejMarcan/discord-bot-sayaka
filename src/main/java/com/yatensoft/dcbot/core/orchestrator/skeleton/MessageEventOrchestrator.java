/** By YamiY Yaten */
package com.yatensoft.dcbot.core.orchestrator.skeleton;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Orchestrator responsible for delegating message event to their respective handlers.
 */
public interface MessageEventOrchestrator {
    /**
     * Handles message received events.
     * @param event message received event
     */
    void handleEvent(MessageReceivedEvent event);
}
