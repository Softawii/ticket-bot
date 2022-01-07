package com.softawii.ticketbot.command;

import com.softawii.ticketbot.annotation.Argument;
import com.softawii.ticketbot.annotation.Command;
import com.softawii.ticketbot.annotation.CommandClass;
import com.softawii.ticketbot.exception.CategoryAlreadyAssignedException;
import com.softawii.ticketbot.exception.CategoryUnassignedException;
import com.softawii.ticketbot.service.DiscordServerRepository;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;

@CommandClass
public class Config {

    private static DiscordServerRepository discordServerRepository = DiscordServerRepository.Companion.getInstance();


    @Command(name="set", description = "Set a chat to a specified category", permissions = {Permission.ADMINISTRATOR})
    @Argument(type= OptionType.BOOLEAN, name="support", description = "Set Current Chat as Support", isRequired = true)
    public static String set(SlashCommandEvent event) {

        boolean supportChat = event.getOption("support").getAsBoolean();

        Long guildId = event.getGuild().getIdLong();

        Category category = event.getTextChannel().getParentCategory();

        if(category == null) {
            return "This channel is not in a category";
        }

        Long serverId = event.getGuild().getIdLong();

        try {
            discordServerRepository.setSupportChat(serverId, category, supportChat);
            if(supportChat) {
                return "Successfully set " + category.getName() + " as support chat";
            } else {
                return "Successfully unset " + category.getName() + " as support chat";
            }
        }
        catch(CategoryAlreadyAssignedException | CategoryUnassignedException e) {
            return e.getMessage();
        }
    }
}
