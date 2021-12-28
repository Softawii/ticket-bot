package command;


import annotation.Command;
import annotation.CommandClass;
import listener.MessageListener;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import service.DiscordTicketService;
import service.TicketService;

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
