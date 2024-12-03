/** By YamiY Yaten */
package com.yatensoft.dcbot.core.enumeration;

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
            if (discordChannelTypeEnum.getValue().equalsIgnoreCase(discordChannelType)) {
                return discordChannelTypeEnum;
            }
        }
        return UNDEFINED;
    }

    public String getValue() {
        return this.value;
    }
}
