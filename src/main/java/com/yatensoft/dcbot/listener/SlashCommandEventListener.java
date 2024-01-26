/** By YamiY Yaten */
package com.yatensoft.dcbot.listener;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

/**
 * Component responsible for slash command events.
 */
@Component
public class SlashCommandEventListener extends ListenerAdapter {

    /** Handle received message events. */
    @Override
    public void onSlashCommandInteraction(final SlashCommandInteractionEvent event) {
        if (event.getName().equals("tag")) {
            event.deferReply().queue(); // Tell discord we received the command, send a thinking... message to the user
            String tagName = event.getOption("name").getAsString();

            System.out.println(tagName);
        }
    }
}
