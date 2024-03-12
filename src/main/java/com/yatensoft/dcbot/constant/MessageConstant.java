/** By YamiY Yaten */
package com.yatensoft.dcbot.constant;

public class MessageConstant {
    /** Templates */
    public static final String TWO_PARTS_MESSAGE_TEMPLATE = "%s %s";

    public static final String THREE_PARTS_MESSAGE_TEMPLATE = "%s %s %s";
    public static final String MENTION_RAW_FORMAT_TEMPLATE = "<@%s>";
    public static final String NEW_VERSION_DEPLOYED_TEMPLATE = "Hello. New version of mine %s was just released.";
    /** Other */
    public static final String REGEX_TO_SPLIT_MESSAGE_ON_WHITESPACE_CHARACTERS = "\\s+";

    public static final String REGEX_URL_EXTRACTION =
            "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
    /** Bot Messages */
    public static final String NO_NEW_ARTICLE_WAS_ADDED =
            "No new articles were added. The newest article can be found in 'news' channel";

    public static final String NEW_ARTICLE_WAS_ADDED_FAB =
            "New article was published on Flesh And Blood page. See at: \n";
    public static final String NEW_ARTICLES_WERE_ADDED_FAB =
            "%d new articles were published on Flesh And Blood page. See at: \n";
    public static final String NEW_BANLIST_WAS_ADDED_YGO = "New banlist was published on Yu-Gi-Oh! page. See at:";
    public static final String UNKNOWN_COMMAND = "I'm sorry, but I do not know this command.";
    public static final String FAB_LATEST_ARTICLE_URL = "Sure. Here is the link to the latest Flesh And Blood article:";
    public static final String YGO_LATEST_BANLIST_URL = "Sure. Here is the link to the latest Yu-Gi-Oh! banlist:";
    public static final String POST_NEW_RECOMMENDATIONS_WITH_CREDITS = "New recommended content from: %s \n";

    public MessageConstant() {}
}
