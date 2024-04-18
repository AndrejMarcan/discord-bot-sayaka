/** By YamiY Yaten */
package com.yatensoft.dcbot.plugin.fab.service.skeleton;

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

    /**
     * Get Living Legend data (leaderboards, points per hero...)
     *
     * @param event message event (may contain optional command parameters)
     */
    void getLivingLegendData(MessageReceivedEvent event) throws IOException;
}
