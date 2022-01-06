package com.softawii.ticketbot.command;

import com.softawii.ticketbot.annotation.Argument;
import com.softawii.ticketbot.annotation.Command;
import com.softawii.ticketbot.annotation.CommandClass;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;

@CommandClass
public class Config {

    @Command(name="set", description = "Set a chat to a specified category")
    @Argument(type= OptionType.BOOLEAN, name="support", description = "Set Current Chat as Support", isRequired = true)
    public static String set(SlashCommandEvent event) {

        boolean supportChat = event.getOption("support").getAsBoolean();

        Long guildId = event.getGuild().getIdLong();

        Category category = event.getTextChannel().getParentCategory();

        if(category == null) {
            return "This channel is not in a category";
        }

        String content = category.getIdLong() + " : " + guildId;

        if(supportChat) event.reply("Que papinho hein" + content + "\n").queue();
        else event.reply(" sem papinho").queue();

        return null;
    }
}
