/** By YamiY Yaten */
package com.yatensoft.dcbot.listener;

import com.yatensoft.dcbot.constant.ChannelConstant;
import com.yatensoft.dcbot.constant.MessageConstant;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/** Service responsible for executing functions right after bot is initialized */
@Service
public class OnReadyEventListener extends ListenerAdapter {
    @Value("${application.version}")
    private String SAYAKA_VERSION;

    /** ReadyEvent is fired after the bot is fully initialized */
    @Override
    public void onReady(ReadyEvent event) {
        final TextChannel generalChannel = event.getJDA().getTextChannelById(ChannelConstant.GENERAL_CHANNEL);
        /* Only on production server TODO replace hardcoded IDs with DB */
        if (generalChannel != null) {
            generalChannel
                    .sendMessage(String.format(MessageConstant.NEW_VERSION_DEPLOYED_TEMPLATE, SAYAKA_VERSION))
                    .queue();
        }
    }
}
