package com.yatensoft.dcbot.service.skeleton;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface MoviesAndSeriesChannelService {
    void handleMessageEvent(MessageReceivedEvent messageEvent);
}
