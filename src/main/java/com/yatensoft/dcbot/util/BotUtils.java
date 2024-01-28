/** By YamiY Yaten */
package com.yatensoft.dcbot.util;

import com.yatensoft.dcbot.config.DiscordBotConfig;
import com.yatensoft.dcbot.constant.BotCommandConstant;
import com.yatensoft.dcbot.constant.MessageConstant;
import net.dv8tion.jda.api.entities.Message;

/**
 * Utility class related to bot.
 */
public class BotUtils {
    /**
     * Check if the text message mentions Sayaka(BOT -> @Sayaka).
     * @param message
     * @return
     */
    public static boolean isSayakaMentioned(Message message) {
        return message.getMentions().getMembers().stream().anyMatch(member -> member.getId()
                .equals(DiscordBotConfig.getBotJDA().getSelfUser().getId()));
    }

    /**
     * Get Sayaka(BOT -> <@ID>) as a mention in raw message context.
     * @return bot as mention
     */
    public static String getSayakaAsMentionInRawFormat() {
        return String.format(
                MessageConstant.MENTION_RAW_FORMAT_TEMPLATE,
                DiscordBotConfig.getBotJDA().getSelfUser().getId());
    }

    /**
     * Validate if the word has the correct command format. Commands are defined with '--' before command tag.
     * @param commandToValidate string to validate
     * @return true if valid
     */
    public static boolean hasValidCommandFormat(final String commandToValidate) {
        if (commandToValidate == null) {
            return false;
        }
        return commandToValidate.startsWith(BotCommandConstant.COMMAND_PREFIX);
    }
}
