/** By YamiY Yaten */
package com.yatensoft.dcbot.component.skeleton.fab;

import com.yatensoft.dcbot.dto.fab.FabPagesListBlockDTO;
import java.io.IOException;
import java.util.List;

/**
 * Class responsible for handling of parsing and processing of websites.
 */
public interface FabWebsiteParser {
    /**
     * Get latest article URL
     * @return list of sorted articles from newest to oldest
     * @throws IOException if unable to parse website
     */
    List<FabPagesListBlockDTO> getLatestArticlesSorted() throws IOException;
}
