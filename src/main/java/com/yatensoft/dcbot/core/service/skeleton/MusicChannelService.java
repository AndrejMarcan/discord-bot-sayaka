/** By YamiY Yaten */
package com.yatensoft.dcbot.core.service.skeleton;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Class responsible for handling operations in #music channel.
 */
public interface MusicChannelService {
    /**
     * Method responsible for handling message received event in #music channel.
     * @param messageEvent message received event
     */
    void handleMessageEvent(MessageReceivedEvent messageEvent);
}
