/** By YamiY Yaten */
package com.yatensoft.dcbot.service.impl;

import com.yatensoft.dcbot.component.impl.FabWebsiteParser;
import com.yatensoft.dcbot.constant.MessageConstant;
import com.yatensoft.dcbot.service.skeleton.FabCommandService;
import java.io.IOException;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation class of CommandService interface responsible for handling of commands
 * related to Flesh And Blood channels.
 */
@Service
public class FabCommandServiceImpl implements FabCommandService {
    private final FabWebsiteParser websiteParser;

    public FabCommandServiceImpl(@Autowired final FabWebsiteParser websiteParser) {
        super();
        this.websiteParser = websiteParser;
    }

    /** See {@link FabCommandService#getLatestArticleURL(MessageReceivedEvent)} */
    @Override
    public void getLatestArticleURL(final MessageReceivedEvent event) throws IOException {
        /* Get the latest article URL */
        final String fetchedUrl = websiteParser.getLatestArticleUrl();
        /* Send a message to the news channel */
        event.getChannel()
                .sendMessage(String.format(
                        MessageConstant.TWO_PARTS_MESSAGE_TEMPLATE, MessageConstant.NEWEST_ARTICLE_URL, fetchedUrl))
                .queue();
    }
}
