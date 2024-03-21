/** By YamiY Yaten */
package com.yatensoft.dcbot.enumeration.fab;

import org.apache.logging.log4j.util.Strings;

/**
 * Enum holding values for Flesh And Blood game formats
 */
public enum FabGameFormatEnum {
    CLASSIC_CONSTRUCTED("cc", "Classic Constructed"),
    BLITZ("blitz", "Blitz"),
    UNDEFINED(Strings.EMPTY, Strings.EMPTY);

    private String shortName;
    private String fullName;

    FabGameFormatEnum(final String shortName, final String fullName) {
        this.shortName = shortName;
        this.fullName = fullName;
    }

    public String getShortName() {
        return this.shortName;
    }

    public String getFullName() {
        return this.fullName;
    }

    /**
     * Get enum from short name while ignoring case
     * @param shortName enum short name
     * @return enum
     */
    public static FabGameFormatEnum getFabGameFormatEnumByShortName(final String shortName) {
        for (FabGameFormatEnum fabGameFormatEnum : FabGameFormatEnum.values()) {
            if (fabGameFormatEnum.getShortName().equalsIgnoreCase(shortName)) {
                return fabGameFormatEnum;
            }
        }
        return null;
    }
}
