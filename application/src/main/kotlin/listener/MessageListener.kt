package listener

import listener.ReadyListener.Companion.getInviteLink
import net.dv8tion.jda.api.entities.ChannelType
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.Interaction
import net.dv8tion.jda.api.interactions.commands.OptionMapping
import service.DiscordTicketService
import service.TicketService

class MessageListener: ListenerAdapter() {

    private val ticketService:TicketService = DiscordTicketService

    companion object {
        private fun checkIfPrivateChannel(event: Interaction): Boolean {
            return if (event.channelType != ChannelType.PRIVATE) {
                event.reply("Só é possível criar um ticket pelas mensagens diretas").queue()
                false
            } else {
                true
            }
        }
        private fun checkIfNotPrivateChannel(event: Interaction): Boolean {
            return !checkIfPrivateChannel(event)
        }
    }

    override fun onSlashCommand(event: SlashCommandEvent) {
        val user = event.user
        val options: List<OptionMapping> = event.options
        when (event.name) {
            "help" -> {
                TODO("Comando ainda não foi implementado")
            }
            "ping" -> {
                val time = System.currentTimeMillis()
                event.reply("Pong!")
                    .setEphemeral(true)
                    .flatMap {
                        event.hook.editOriginalFormat("Pong: %d ms", System.currentTimeMillis() - time)
                    }.queue()
            }
            "invite" -> {
                event.reply(getInviteLink(event.jda)).queue()
            }
            "create-ticket" -> {
                if (checkIfNotPrivateChannel(event)) return
                val response = ticketService.createTicket(user.idLong)
                event.reply(response).queue()
            }
            "change-ticket" -> {
                if (checkIfNotPrivateChannel(event)) return
                TODO("Comando ainda não foi implementado")
            }
            "archive-ticket" -> {
                if (checkIfNotPrivateChannel(event)) return
                val ticketIdOption: OptionMapping = options[0]
                val ticketId = ticketIdOption.asLong
                val response = ticketService.archiveTicket(user.idLong, ticketId)
                event.reply(response).queue()
            }
            "archive-current-ticket" -> {
                if (checkIfNotPrivateChannel(event)) return
                val response = ticketService.archiveCurrentTicket(user.idLong)
                event.reply(response).queue()
            }
            "send-message" -> {
                TODO("Comando ainda não foi implementado")
            }
            "active-ticket" -> {
                if (checkIfNotPrivateChannel(event)) return
                val response = ticketService.activeTicket(user.idLong)
                event.reply(response).queue()
            }
        }
    }
}