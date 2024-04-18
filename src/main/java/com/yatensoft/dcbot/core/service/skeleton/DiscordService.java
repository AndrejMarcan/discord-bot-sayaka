/** By YamiY Yaten */
package com.yatensoft.dcbot.core.service.skeleton;

import com.yatensoft.dcbot.core.enumeration.SayakaManagedServerEnum;

/**
 * Service class responsible for providing discord data
 */
public interface DiscordService {
    /**
     * Provides discord channel ID based for given server and channel key
     * @param sayakaManagedServerEnum server
     * @param discordChannelKey channel key
     * @return discord channel ID
     */
    long getChannelIdByServerAndChannelKey(SayakaManagedServerEnum sayakaManagedServerEnum, String discordChannelKey);
}
