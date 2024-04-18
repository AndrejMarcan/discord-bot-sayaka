/** By YamiY Yaten */
package com.yatensoft.dcbot.core.enumeration;

/**
 * Enum holding server names of discord servers that are managed by Sayaka(dc-bot)
 */
public enum SayakaManagedServerEnum {
    KITCHEN_TABLE_TCGS("KitchenTableTCGs");

    private String serverName;

    SayakaManagedServerEnum(final String serverName) {
        this.serverName = serverName;
    }

    public String getServerName() {
        return this.serverName;
    }
}
