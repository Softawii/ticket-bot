package com.softawii.ticketbot.command;

import com.softawii.ticketbot.annotation.Command;
import com.softawii.ticketbot.service.DiscordTicketService;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

import java.io.Serializable;

public class Utils implements Serializable {

    @Command(name = "aaa")
    public static String invite(SlashCommandEvent event) {
        return DiscordTicketService.getInviteLink(event.getJDA());
    }

    @Command
    public static String aaa(SlashCommandEvent event) {
        return "TODO: Invite";
    }

    @Command
    public static String ping(SlashCommandEvent event) {
        long time = System.currentTimeMillis();

        event.reply("Pong!")
                .flatMap(v ->
                        event.getHook().editOriginalFormat("Pong! %d ms", + (System.currentTimeMillis() - time))).queue();

        return null;
    }
}
