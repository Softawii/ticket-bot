package com.softawii.ticketbot.command;


import com.softawii.ticketbot.annotation.Argument;
import com.softawii.ticketbot.annotation.Arguments;
import com.softawii.ticketbot.annotation.Command;
import com.softawii.ticketbot.annotation.CommandClass;
import com.softawii.ticketbot.service.DiscordService;
import com.softawii.ticketbot.service.TicketService;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.util.Objects;

@CommandClass
public class Tickets {

    private static final TicketService ticketService = DiscordService.INSTANCE;

    @Command(name = "create-ticket", description = "Cria um novo ticket")
    public static String createTicket(SlashCommandEvent event) {
        Guild guild = event.getGuild();

        final String response = ticketService.createTicket(event.getUser().getIdLong(), guild.getIdLong());


        DiscordService discordService = DiscordService.INSTANCE;
        final String response2 = discordService.redirectTicket(event);

        if (response2 != null) {
            event.reply(response2).queue();
        } else {
            event.getUser().openPrivateChannel().flatMap(channel -> channel.sendMessage(response)).queue();
            event.reply("Ticket criado com sucesso!, Olhe seu privado").queue();
        }

        return null;
    }

    @Command(name = "switch-ticket", description = "Troca para um ticket já existente")
    @Argument(type = OptionType.INTEGER, name = "ticket-id", description = "Ticket ID", isRequired = true)
    public static String switchTicket(SlashCommandEvent event) {
        return "Comando ainda não foi implementado";
    }

    @Command(name = "archive-ticket", description = "Arquiva um ticket já existente")
    @Argument(type = OptionType.INTEGER, name = "ticket-id", description = "Ticket ID", isRequired = true)
    public static String archiveTicket(SlashCommandEvent event) {
        User user = event.getUser();
        OptionMapping ticketIdOption = Objects.requireNonNull(event.getOption("ticket-id"));
        Long ticketId = ticketIdOption.getAsLong();
        String response = ticketService.archiveTicket(user.getIdLong(), ticketId);
        return response;
    }

    @Command(name = "archive-current-ticket", description = "Arquiva o ticket ativo")
    public static String archiveCurrentTicket(SlashCommandEvent event) {
        User user = event.getUser();
        String response = ticketService.archiveCurrentTicket(user.getIdLong());
        return response;
    }

    @Command(name = "send-message", description = "Envia uma mensagem para um ticket")
    @Arguments({
            @Argument(type = OptionType.INTEGER, name = "ticket-id", description = "Ticket ID", isRequired = true),
            @Argument(type = OptionType.STRING, name = "message", description = "Mensagem", isRequired = true)
    })
    public static String sendMessage(SlashCommandEvent event) {
        event.getOptions().forEach(System.out::println);
        return "Comando ainda não foi implementado";
    }

    @Command(name = "active-ticket", description = "Informa o ticket ativo")
    public static String activeTicket(SlashCommandEvent event) {
        User user = event.getUser();
        String response = ticketService.activeTicket(user.getIdLong());
        return response;
    }
}
