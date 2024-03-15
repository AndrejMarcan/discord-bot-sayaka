/** By YamiY Yaten */
package com.yatensoft.dcbot.enumeration;

public enum DiscordChannelTypeEnum {
    TEXT("text"),
    VOICE("voice");

    private String value;

    DiscordChannelTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
