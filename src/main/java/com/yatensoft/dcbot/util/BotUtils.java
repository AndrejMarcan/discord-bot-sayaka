package com.yatensoft.dcbot.util;

import com.yatensoft.dcbot.config.DiscordBotConfig;
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
}
