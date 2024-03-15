/** By YamiY Yaten */
package com.yatensoft.dcbot.config;

import com.yatensoft.dcbot.listener.MessageEventListener;
import com.yatensoft.dcbot.listener.OnReadyEventListener;
import jakarta.annotation.PostConstruct;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/** Base initialization class. */
@Configuration
@EnableScheduling
public class SayakaConfig {
    @Value("${discord.bot.token}")
    private String SAYAKA_BOT_TOKEN;

    private final MessageEventListener messageEventListener;
    private final OnReadyEventListener onReadyEventListener;

    private static JDA sayaka;

    SayakaConfig(
            @Autowired final MessageEventListener messageEventListener,
            @Autowired final OnReadyEventListener onReadyEventListener) {
        super();
        this.messageEventListener = messageEventListener;
        this.onReadyEventListener = onReadyEventListener;
    }

    /** JDA initialization. */
    @PostConstruct
    private void init() {
        this.sayaka = JDABuilder.createDefault(SAYAKA_BOT_TOKEN)
                .addEventListeners(messageEventListener, onReadyEventListener)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .build();
    }

    /** Get JDA instance. */
    public static JDA getSayaka() {
        return sayaka;
    }
}
