/** By YamiY Yaten */
package com.yatensoft.dcbot.enumeration;

public enum DiscordChannelTypeEnum {
    TEXT("text"),
    VOICE("voice"),
    UNDEFINED("undefined");

    private String value;

    DiscordChannelTypeEnum(String value) {
        this.value = value;
    }

    public static DiscordChannelTypeEnum getDiscordChannelTypeFromString(final String discordChannelType) {
        for (DiscordChannelTypeEnum discordChannelTypeEnum : DiscordChannelTypeEnum.values()) {
            if (discordChannelTypeEnum.getValue().equals(discordChannelType)) {
                return discordChannelTypeEnum;
            }
        }
        return UNDEFINED;
    }

    public String getValue() {
        return this.value;
    }
}
