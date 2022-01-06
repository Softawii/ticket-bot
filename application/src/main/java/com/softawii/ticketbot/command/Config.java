package com.softawii.ticketbot.command;

import com.softawii.ticketbot.annotation.Argument;
import com.softawii.ticketbot.annotation.Command;
import com.softawii.ticketbot.annotation.CommandClass;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;

@CommandClass
public class Config {

    @Command(name="set", description = "Set a chat to a specified category")
    @Argument(type= OptionType.BOOLEAN, name="support", description = "Set Current Chat as Support", isRequired = true)
    public static String set(SlashCommandEvent event) {

        System.out.println("In Config.set");

        boolean supportChat = event.getOption("support").getAsBoolean();

        System.out.println("supportChat: " + supportChat);

        if(supportChat) event.reply("Que papinho hein\n").queue();
        else event.reply(" sem papinho").queue();

        return null;
    }
}
