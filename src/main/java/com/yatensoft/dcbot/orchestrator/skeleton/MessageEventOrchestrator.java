package com.yatensoft.dcbot.orchestrator.skeleton;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface MessageEventOrchestrator {
    void delegateEvent(MessageReceivedEvent event);
}
