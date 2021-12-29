package com.softawii.ticketbot.listener

import com.softawii.ticketbot.internal.CommandHandler
import com.softawii.ticketbot.service.DiscordTicketService
import com.softawii.ticketbot.service.TicketService
import net.dv8tion.jda.api.entities.ChannelType
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.Interaction
import net.dv8tion.jda.api.interactions.commands.OptionMapping
import org.apache.logging.log4j.LogManager

class MessageListener: ListenerAdapter() {

    private val ticketService: TicketService = DiscordTicketService

    companion object {
        val LOGGER = LogManager.getLogger(Companion::class.java)
        val COMMANDS = mutableMapOf<String, CommandHandler>()

        @JvmStatic
        @Deprecated("New way of validate channel type", ReplaceWith("CommandHandler"), DeprecationLevel.WARNING)
        fun checkIfPrivateChannel(event: Interaction): Boolean {
            return if (event.channelType != ChannelType.PRIVATE) {
                event.reply("Só é possível criar um ticket pelas mensagens diretas").queue()
                false
            } else {
                true
            }
        }

        @JvmStatic
        @Deprecated("New way of validate channel type", ReplaceWith("CommandHandler"), DeprecationLevel.WARNING)
        fun checkIfNotPrivateChannel(event: Interaction): Boolean {
            return !checkIfPrivateChannel(event)
        }
    }

    override fun onSlashCommand(event: SlashCommandEvent) {
        val user = event.user
        val options: List<OptionMapping> = event.options

        val command = event.name

        val result =  COMMANDS[command]!!.execute(event) as String?
        if (result != null) event.reply(result).queue()
    }
}