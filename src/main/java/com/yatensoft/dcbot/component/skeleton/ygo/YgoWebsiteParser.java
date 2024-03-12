/** By YamiY Yaten */
package com.yatensoft.dcbot.component.skeleton.ygo;

import java.io.IOException;

public interface YgoWebsiteParser {
    /**
     * Get latest ban-list URL
     * @return url of the last article
     * @throws IOException if unable to parse website
     */
    String getLatestBanListUrl() throws IOException;
}
