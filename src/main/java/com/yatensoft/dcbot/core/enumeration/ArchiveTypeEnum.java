/** By YamiY Yaten */
package com.yatensoft.dcbot.core.enumeration;

/**
 * Enum holding values for Article Types
 */
public enum ArchiveTypeEnum {
    ARTICLE("article"),
    MUSIC("music"),
    VIDEO("video"),
    BANLIST("banlist");

    private String value;

    ArchiveTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
