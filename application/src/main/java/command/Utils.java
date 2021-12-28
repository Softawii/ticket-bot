package command;

import annotation.Command;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

import java.io.Serializable;

public class Utils implements Serializable {

    @Command
    public static String invite(SlashCommandEvent event) {
        // TODO: "Comando ainda não foi implementado"
        return "Comando ainda não foi implementado";
    }

    @Command
    public static String help(SlashCommandEvent event) {
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
