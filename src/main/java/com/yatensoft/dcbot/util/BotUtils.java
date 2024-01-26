package com.yatensoft.dcbot.util;

import com.yatensoft.dcbot.config.DiscordBotConfig;
import com.yatensoft.dcbot.constant.MessageConstant;
import net.dv8tion.jda.api.entities.Message;

public class BotUtils {
    /**
     * Check if the text message mentions Sayaka(BOT -> @Sayaka)
     * @param message
     * @return
     */
    public static boolean isSayakaMentioned(Message message) {
        return message.getMentions().getMembers().stream()
                .anyMatch(member -> member.getId()
                        .equals(DiscordBotConfig.getBotJDA().getSelfUser().getId()));
    }

    /**
     * Get bot as a mention in raw message context
     * @return bot as mention
     */
    public static String getBotAsMentionInRawFormat() {
        return String.format(MessageConstant.MENTION_RAW_FORMAT_TEMPLATE, DiscordBotConfig.getBotJDA().getSelfUser().getId());
    }
}
