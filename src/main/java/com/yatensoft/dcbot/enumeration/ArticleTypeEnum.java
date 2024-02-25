/** By YamiY Yaten */
package com.yatensoft.dcbot.enumeration;

/**
 * Enum holding values for Article Types
 */
public enum ArticleTypeEnum {
    ARTICLE("article"),
    BANLIST("banlist");

    private String value;

    ArticleTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
