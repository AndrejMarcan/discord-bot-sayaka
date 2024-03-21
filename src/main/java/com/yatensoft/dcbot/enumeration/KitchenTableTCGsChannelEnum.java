/** By YamiY Yaten */
package com.yatensoft.dcbot.enumeration;

/**
 * Enum holding channel names of KitchenTableTCGs server
 */
public enum KitchenTableTCGsChannelEnum {
    GENERAL_GENERAL("GENERAL_general"),
    GENERAL_RECOMMENDATIONS("GENERAL_recommendations"),
    GENERAL_OTHER_SHIT("GENERAL_other-shit"),
    ENTERTAINMENT_MEMES("ENTERTAINMENT_memes"),
    ENTERTAINMENT_MOVIES_AND_SERIES("ENTERTAINMENT_movies-and-series"),
    ENTERTAINMENT_MUSIC("ENTERTAINMENT_music"),
    ENTERTAINMENT_REC_MOVIES_AND_SERIES("ENTERTAINMENT_rec-movies-and-series"),
    ENTERTAINMENT_REC_MUSIC("ENTERTAINMENT_rec-music"),
    FLESH_AND_BLOOD_CHAT("FLESH-AND-BLOOD_chat"),
    FLESH_AND_BLOOD_NEWS("FLESH-AND-BLOOD_news"),
    FLESH_AND_BLOOD_DECKS("FLESH-AND-BLOOD_decks"),
    FLESH_AND_BLOOD_EVENTS("FLESH-AND-BLOOD_events"),
    FLESH_AND_BLOOD_TRADING_ROOM("FLESH-AND-BLOOD_trading-room"),
    FLESH_AND_BLOOD_ONLINE_PLAY("FLESH-AND-BLOOD_online-play"),
    YU_GI_OH_CHAT("YU-GI-OH!_chat"),
    YU_GI_OH_NEWS("YU-GI-OH!_news"),
    YU_GI_OH_DECKS("YU-GI-OH!_decks"),
    YU_GI_OH_EVENTS("YU-GI-OH!_events"),
    YU_GI_OH_TRADING_ROOM("YU-GI-OH!_trading-room"),
    OTHER_TCGS_CARDFIGHT_VANGUARD("OTHER-TCGS_cardfight-vanguard"),
    SAYAKA_BOT_SAYAKA_DOCS("SAYAKA_bot-sayaka-docs"),
    SAYAKA_CHANGELOG("SAYAKA_changelog"),
    VOICE_GENERAL("VOICE_general"),
    VOICE_PLAYROOM("VOICE_playroom"),
    VOICE_PLAYROOM_2("VOICE_playroom-2"),
    VOICE_PLAYROOM_3("VOICE_playroom-3");

    private final String channelKey;

    KitchenTableTCGsChannelEnum(final String channelKey) {
        this.channelKey = channelKey;
    }

    public String getChannelKey() {
        return channelKey;
    }
}
