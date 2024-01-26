/** By YamiY Yaten */
package com.yatensoft.dcbot.service.impl.fab;

import com.yatensoft.dcbot.constant.MessageConstant;
import com.yatensoft.dcbot.service.skeleton.CommandService;
import java.io.IOException;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation class of CommandService interface responsible for handling of commands
 * related to Flesh And Blood channels.
 */
@Service
public class FabCommandServiceImpl implements CommandService {
    private final FabWebsiteParser websiteParser;

    public FabCommandServiceImpl(@Autowired final FabWebsiteParser websiteParser) {
        super();
        this.websiteParser = websiteParser;
    }

    // TODO ADD HANDLING TO PARTICULAR COMMAND (private methods)
    /** See {@link CommandService#handleCommand(String, MessageReceivedEvent)} */
    @Override
    public void handleCommand(final String command, final MessageReceivedEvent event) throws IOException {
        /* Get the latest article URL */
        final String fetchedUrl = websiteParser.getLatestArticleUrl();
        /* Send a message to the news channel */
        event.getChannel()
                .sendMessage(String.format(
                        MessageConstant.TWO_PARTS_MESSAGE_TEMPLATE, MessageConstant.NEWEST_ARTICLE_URL, fetchedUrl))
                .queue();
    }
}
