/** By YamiY Yaten */
package com.yatensoft.dcbot.component.skeleton.fab;

import com.yatensoft.dcbot.dto.fab.FabArticleDTO;
import com.yatensoft.dcbot.dto.fab.FabLivingLegendElementDTO;
import com.yatensoft.dcbot.enumeration.fab.FabGameFormatEnum;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Class responsible for handling of parsing and processing of websites.
 */
public interface FabWebsiteParser {
    /**
     * Get latest article URL
     * @return list of the latest articles
     * @throws IOException if unable to parse website
     */
    List<FabArticleDTO> getLatestArticles() throws IOException;

    /**
     * Get Living Legend Leaderboard for given game format
     * @return List of heros and their living legends progress data
     * @throws IOException if unable to parse website
     */
    Map<FabGameFormatEnum, List<FabLivingLegendElementDTO>> getLivingLegendLeaderboards(FabGameFormatEnum format)
            throws IOException;
}
