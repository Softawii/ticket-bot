package com.softawii.ticketbot.command;

import com.softawii.ticketbot.annotation.Command;
import com.softawii.ticketbot.service.DiscordService;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

import java.io.Serializable;

public class Utils implements Serializable {

    @Command(description = "Convite do bot")
    public static String invite(SlashCommandEvent event) {
        return DiscordService.getInviteLink(event.getJDA());
    }

    @Command(description = "Ajuda")
    public static String help(SlashCommandEvent event) {
        return "TODO: Invite";
    }

    @Command(description = "Pong")
    public static String ping(SlashCommandEvent event) {
        long time = System.currentTimeMillis();

        event.reply("Pong!")
                .flatMap(v ->
                        event.getHook().editOriginalFormat("Pong! %d ms", + (System.currentTimeMillis() - time))).queue();

        return null;
    }
}
