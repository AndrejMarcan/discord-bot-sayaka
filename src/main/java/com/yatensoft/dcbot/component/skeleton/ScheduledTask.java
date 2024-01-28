/** By YamiY Yaten */
package com.yatensoft.dcbot.component.skeleton;

import java.io.IOException;

/**
 * Class responsible for handling of scheduled operations.
 */
public interface ScheduledTask {
    /**
     * Checks if there was added new article to website and send a message to discord channel in case of
     * positive scenario
     *
     * @throws IOException if parsing fails
     */
    void checkFabtcgArticles() throws IOException;
}
