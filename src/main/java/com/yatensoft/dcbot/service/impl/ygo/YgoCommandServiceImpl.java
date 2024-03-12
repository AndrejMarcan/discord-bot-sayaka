/** By YamiY Yaten */
package com.yatensoft.dcbot.service.impl.ygo;

import com.yatensoft.dcbot.component.skeleton.ygo.YgoWebsiteParser;
import com.yatensoft.dcbot.constant.MessageConstant;
import com.yatensoft.dcbot.service.skeleton.ygo.YgoCommandService;
import java.io.IOException;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation class of CommandService interface responsible for handling of commands
 * related to Yu-Gi-Oh! channels.
 */
@Service
public class YgoCommandServiceImpl implements YgoCommandService {
    private final YgoWebsiteParser websiteParser;

    public YgoCommandServiceImpl(@Autowired final YgoWebsiteParser websiteParser) {
        super();
        this.websiteParser = websiteParser;
    }

    /** See {@link YgoCommandService#getLatestBanListUrl(MessageReceivedEvent)} */
    @Override
    public void getLatestBanListUrl(final MessageReceivedEvent event) throws IOException {
        /* Get the latest article URL */
        final String fetchedUrl = websiteParser.getLatestBanListUrl();
        /* Send a message to the news channel */
        event.getChannel()
                .sendMessage(String.format(
                        MessageConstant.TWO_PARTS_MESSAGE_TEMPLATE, MessageConstant.YGO_LATEST_BANLIST_URL, fetchedUrl))
                .queue();
    }
}
