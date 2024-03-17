/** By YamiY Yaten */
package com.yatensoft.dcbot.service.skeleton;

import com.yatensoft.dcbot.enumeration.SayakaManagedServerEnum;

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
