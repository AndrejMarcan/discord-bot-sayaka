/** By YamiY Yaten */
package com.yatensoft.dcbot.enumeration;

/**
 * Enum holding values for Topics
 */
public enum TopicEnum {
    FLESH_AND_BLOOD("fab"),
    YUGIOH("ygo"),
    COMMON("common");

    private String shortName;

    TopicEnum(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return this.shortName;
    }
}
