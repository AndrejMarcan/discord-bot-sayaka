/** By YamiY Yaten */
package com.yatensoft.dcbot.core.constant;

public class WebsiteParserConstant {
    /** fabtcg.com -> Articles Page */
    public static final String FABTCG_PAGES_LIST_BLOCK_ID = "pagesListblock";

    public static final String FABTCG_DATE_FORMAT = "MMM dd, yyyy";
    public static final String FABTCG_ITEM_LINK_CLASS = "item-link";
    public static final String FABTCG_URL_ELEMENT_CSS_QUERY = "a.item-link";
    public static final String FABTCG_TITLE_ELEMENT_CSS_QUERY = ".item-card div.item-card-inner h5";
    public static final String FABTCG_DATE_ELEMENT_CSS_QUERY = ".item-card div.item-card-inner p";

    /** fabtcg.com -> Living Legend Page */
    /* Already obtained Living Legend status in Classic Constructed */
    public static final String FABTCG_CC_LL_BOARD_SELECTOR =
            "body > article > div > div.container.page-content.py-3.px-0 > div:nth-child(5) > table";
    /* Living Legend status not yet obtained in Classic Constructed */
    public static final String FABTCG_CC_LL_LEADERBOARD_SELECTOR =
            "body > article > div > div.container.page-content.py-3.px-0 > div:nth-child(6) > table";
    /* Already obtained Living Legend status in Blitz */
    public static final String FABTCG_BLITZ_LL_BOARD_SELECTOR =
            "body > article > div > div.container.page-content.py-3.px-0 > div:nth-child(8) > table";
    /* Living Legend status not yet obtained in Blitz */
    public static final String FABTCG_BLITZ_LL_LEADERBOARD_SELECTOR =
            "body > article > div > div.container.page-content.py-3.px-0 > div:nth-child(9) > table";
    public static final String FABTCG_TABLE_BODY = "tbody tr";
    public static final String FABTCG_TD = "td";

    /** yugioh-card.com -> Banlist */
    public static final String YGOCARD_BLOCK_BUTTON_CLASS = "wp-block-button__link";

    /** Common Constants */
    public static final String HREF = "href";

    public WebsiteParserConstant() {}
}
