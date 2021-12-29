package com.softawii.ticketbot.command;


import com.softawii.ticketbot.annotation.Argument;
import com.softawii.ticketbot.annotation.Command;
import com.softawii.ticketbot.annotation.CommandClass;
import com.softawii.ticketbot.listener.MessageListener;
import com.softawii.ticketbot.service.DiscordTicketService;
import com.softawii.ticketbot.service.TicketService;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.util.Objects;

@CommandClass
public class Tickets {

    private static final TicketService ticketService = DiscordTicketService.INSTANCE;

    @Command(name = "create-ticket")
    @Argument(type = OptionType.INTEGER, name = "ticket-id", description = "Ticket ID", isRequired = true)
    public static String createTicket(SlashCommandEvent event) {
        if (MessageListener.checkIfNotPrivateChannel(event)) {
            return "This command can only be used in a private channel.";
        }
        String response = ticketService.createTicket(event.getUser().getIdLong());
        event.reply(response).queue();

        return null;
    }

    @Command(name = "switch-ticket")
    @Argument(type = OptionType.INTEGER, name = "ticket-id", description = "Ticket ID", isRequired = true)
    public static String switchTicket(SlashCommandEvent event) {
        if (MessageListener.checkIfNotPrivateChannel(event)) {
            return "This command can only be used in a private channel.";
        }
        return "Comando ainda não foi implementado";
    }

    @Command(name = "archive-ticket")
    @Argument(type = OptionType.INTEGER, name = "ticket-id", description = "Ticket ID", isRequired = true)
    public static String archiveTicket(SlashCommandEvent event) {
        if (MessageListener.checkIfNotPrivateChannel(event)) {
            return "This command can only be used in a private channel.";
        }
        User user = event.getUser();
        OptionMapping ticketIdOption = Objects.requireNonNull(event.getOption("ticket-id"));
        Long ticketId = ticketIdOption.getAsLong();
        String response = ticketService.archiveTicket(user.getIdLong(), ticketId);
        return response;
    }

    @Command(name = "archive-current-ticket")
    public static String archiveCurrentTicket(SlashCommandEvent event) {
        if (MessageListener.checkIfNotPrivateChannel(event)) {
            return "This command can only be used in a private channel.";
        }
        User user = event.getUser();
        String response = ticketService.archiveCurrentTicket(user.getIdLong());
        return response;
    }

    @Command(name = "send-message")
    @Argument(type = OptionType.INTEGER, name = "ticket-id", description = "Ticket ID", isRequired = true)
    public static String sendMessage(SlashCommandEvent event) {
        if (MessageListener.checkIfNotPrivateChannel(event)) {
            return "This command can only be used in a private channel.";
        }
        return "Comando ainda não foi implementado";
    }

    @Command(name = "active-ticket")
    public static String activeTicket(SlashCommandEvent event) {
        if (MessageListener.checkIfNotPrivateChannel(event)) {
            return "This command can only be used in a private channel.";
        }
        User user = event.getUser();
        String response = ticketService.activeTicket(user.getIdLong());
        return response;
    }
}
