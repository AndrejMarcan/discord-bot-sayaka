/** By YamiY Yaten */
package com.yatensoft.dcbot.constant;

public class BotCommandConstant {
    /** Command Prefixes */
    public static final String COMMAND_PREFIX = "--";

    public static final String FAB_COMMAND_PREFIX = "--fab";

    public static final String YGO_COMMAND_PREFIX = "--ygo";

    /** Flesh And Blood */
    public static final String FAB_COMMAND_GET_LATEST_ARTICLE_URL = "--fabLatestArticle";

    public static final String FAB_COMMAND_GET_LIVING_LEGEND_DATA = "--fabLivingLegend";

    /** Yu-Gi-Oh! */
    public static final String YGO_COMMAND_GET_LATEST_BANLIST_URL = "--ygoBanlist";

    /** Papameter */
    public static final char COMMAND_LIST_PARAMETER_START_CHARACTER = '[';

    public static final char COMMAND_LIST_PARAMETER_END_CHARACTER = ']';
    public static final String COMMAND_LIST_PARAMETER_VALUE_SPLIT_REGEX = ",";

    public BotCommandConstant() {}
}
