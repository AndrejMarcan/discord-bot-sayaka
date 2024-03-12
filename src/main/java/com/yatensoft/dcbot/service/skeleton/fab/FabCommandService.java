/** By YamiY Yaten */
package com.yatensoft.dcbot.service.skeleton.fab;

import java.io.IOException;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Class responsible for handling of Flesh And Blood related commands.
 */
public interface FabCommandService {
    /**
     * Get the latest article URL from Flesh And Blood website.
     *
     * @param event message event
     * @throws IOException
     */
    void getLatestArticleURL(MessageReceivedEvent event) throws IOException;
}
