package com.softawii.ticketbot.listener

import net.dv8tion.jda.api.entities.ChannelType
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.Interaction
import net.dv8tion.jda.api.interactions.commands.OptionMapping
import org.apache.logging.log4j.LogManager
import com.softawii.ticketbot.service.DiscordTicketService
import com.softawii.ticketbot.service.TicketService
import java.lang.reflect.Method

class MessageListener: ListenerAdapter() {

    private val ticketService: TicketService = DiscordTicketService

    companion object {
        val LOGGER = LogManager.getLogger(Companion::class.java)
        val COMMANDS = mutableMapOf<String, Method>()
        val COMPANION = mutableMapOf<String, Object?>()

        @JvmStatic
        fun checkIfPrivateChannel(event: Interaction): Boolean {
            return if (event.channelType != ChannelType.PRIVATE) {
                event.reply("Só é possível criar um ticket pelas mensagens diretas").queue()
                false
            } else {
                true
            }
        }

        @JvmStatic
        fun checkIfNotPrivateChannel(event: Interaction): Boolean {
            return !checkIfPrivateChannel(event)
        }
    }

    override fun onSlashCommand(event: SlashCommandEvent) {
        val user = event.user
        val options: List<OptionMapping> = event.options

        val command = event.name.replace("-", "").lowercase()

        val result =  COMMANDS[command]!!.invoke(null, event) as String?
        if (result != null) event.reply(result).queue()

        return


        when (event.name) {
            // Tickets
            "change-ticket" -> {
                if (checkIfNotPrivateChannel(event)) return
                TODO("Comando ainda não foi implementado")
            }
            // Tickets
            "archive-ticket" -> {
                if (checkIfNotPrivateChannel(event)) return
                val ticketIdOption: OptionMapping = options[0]
                val ticketId = ticketIdOption.asLong
                val response = ticketService.archiveTicket(user.idLong, ticketId)
                event.reply(response).queue()
            }
            // Tickets
            "archive-current-ticket" -> {
                if (checkIfNotPrivateChannel(event)) return
                val response = ticketService.archiveCurrentTicket(user.idLong)
                event.reply(response).queue()
            }
            // Tickets
            "send-message" -> {
                TODO("Comando ainda não foi implementado")
            }
            // Tickets
            "active-ticket" -> {
                if (checkIfNotPrivateChannel(event)) return
                val response = ticketService.activeTicket(user.idLong)
                event.reply(response).queue()
            }
        }
    }
}