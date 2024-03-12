/** By YamiY Yaten */
package com.yatensoft.dcbot.service.impl.fab;

import com.yatensoft.dcbot.component.impl.fab.FabWebsiteParserImpl;
import com.yatensoft.dcbot.constant.MessageConstant;
import com.yatensoft.dcbot.dto.fab.FabArticleDTO;
import com.yatensoft.dcbot.service.skeleton.fab.FabCommandService;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation class of CommandService interface responsible for handling of commands
 * related to Flesh And Blood channels.
 */
@Service
public class FabCommandServiceImpl implements FabCommandService {
    private final FabWebsiteParserImpl websiteParser;

    public FabCommandServiceImpl(@Autowired final FabWebsiteParserImpl websiteParser) {
        super();
        this.websiteParser = websiteParser;
    }

    // TODO pull article from DB
    /** See {@link FabCommandService#getLatestArticleURL(MessageReceivedEvent)} */
    @Override
    public void getLatestArticleURL(final MessageReceivedEvent event) throws IOException {
        /* Get the latest article URL */
        final List<FabArticleDTO> fetchedUrls = websiteParser.getLatestArticles().stream()
                .sorted(Comparator.comparing(FabArticleDTO::getDate).reversed())
                .toList();
        /* Send a message to the news channel */
        event.getChannel()
                .sendMessage(String.format(
                        MessageConstant.TWO_PARTS_MESSAGE_TEMPLATE,
                        MessageConstant.FAB_LATEST_ARTICLE_URL,
                        fetchedUrls.get(0).getUrl()))
                .queue();
    }
}
