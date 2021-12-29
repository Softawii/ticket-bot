package com.softawii.ticketbot.command;


import com.softawii.ticketbot.annotation.Command;
import com.softawii.ticketbot.annotation.CommandClass;
import com.softawii.ticketbot.listener.MessageListener;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import com.softawii.ticketbot.service.DiscordTicketService;
import com.softawii.ticketbot.service.TicketService;

@CommandClass
public class Tickets {

    private static final TicketService ticketService = DiscordTicketService.INSTANCE;

    @Command
    public static String createTicket(SlashCommandEvent event) {
        if (MessageListener.checkIfNotPrivateChannel(event)) {
            return "This command can only be used in a private channel.";
        }
        String response = ticketService.createTicket(event.getUser().getIdLong());
        event.reply(response).queue();

        return null;
    }
}
