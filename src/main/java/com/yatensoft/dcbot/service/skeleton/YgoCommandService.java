/** By YamiY Yaten */
package com.yatensoft.dcbot.service.skeleton;

import java.io.IOException;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Class responsible for handling of Yu-Gi-Oh! related commands.
 */
public interface YgoCommandService {
    /**
     * Get the latest ban-list.
     *
     * @param event message event
     * @throws IOException
     */
    void getLatestBanListUrl(MessageReceivedEvent event) throws IOException;
}
