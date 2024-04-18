/** By YamiY Yaten */
package com.yatensoft.dcbot.plugin.ygo.component.skeleton;

import java.io.IOException;

/**
 * Class responsible for handling of scheduled operations related to Yu-Gi-Oh! channels.
 */
public interface YgoScheduledTask {
    /**
     * Checks if there was banlist update on the website.
     *
     * @throws IOException if parsing fails
     */
    void checkBanlist() throws IOException;
}
