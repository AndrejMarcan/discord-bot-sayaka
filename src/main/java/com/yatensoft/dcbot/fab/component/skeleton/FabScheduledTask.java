/** By YamiY Yaten */
package com.yatensoft.dcbot.fab.component.skeleton;

import java.io.IOException;

/**
 * Class responsible for handling of scheduled operations related to Flesh And Blood channels.
 */
public interface FabScheduledTask {
    /**
     * Checks if there was added new article to website and send a message to discord channel in case of
     * positive scenario.
     *
     * @throws IOException if parsing fails
     */
    void checkLatestArticles() throws IOException;
}
