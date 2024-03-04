/** By YamiY Yaten */
package com.yatensoft.dcbot.service.skeleton;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Class responsible for handling operations in #movies-and-series channel.
 */
public interface MoviesAndSeriesChannelService {
    /**
     * Method responsible for handling message received event in #movies-and-series channel.
     * @param messageEvent message received event
     */
    void handleMessageEvent(MessageReceivedEvent messageEvent);
}
