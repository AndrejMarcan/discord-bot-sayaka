/** By YamiY Yaten */
package com.yatensoft.dcbot.util;

import com.yatensoft.dcbot.config.DiscordBotConfig;
import com.yatensoft.dcbot.constant.BotCommandConstant;
import com.yatensoft.dcbot.constant.MessageConstant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.dv8tion.jda.api.entities.Message;
import org.jsoup.internal.StringUtil;

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

    /**
     * Collect all URLs from the message
     * @param message message received
     * @return list of URLs
     */
    public static List<String> collectUrlsFromText(final Message message) {
        if (message == null) {
            return new ArrayList<>();
        }
        final String text = message.getContentRaw();
        if (StringUtil.isBlank(text)) {
            return new ArrayList<>();
        }

        List<String> urls = new ArrayList<>();

        final Pattern pattern = Pattern.compile(MessageConstant.REGEX_URL_EXTRACTION, Pattern.CASE_INSENSITIVE);
        final Matcher urlMatcher = pattern.matcher(text);

        while (urlMatcher.find()) {
            urls.add(text.substring(urlMatcher.start(0), urlMatcher.end(0)));
        }

        return urls;
    }
}
