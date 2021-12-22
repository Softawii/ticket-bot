package listener

import listener.ReadyListener.Companion.getInviteLink
import net.dv8tion.jda.api.entities.ChannelType
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.Interaction
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyAction

class MessageListener: ListenerAdapter() {

    companion object {
        fun checkIfPrivateChannel(event: Interaction): Boolean {
            if (event.channelType != ChannelType.PRIVATE) {
                event.reply("Só é possível criar um ticket pelas mensagens diretas").queue()
                return false
            } else {
                return true
            }
        }
    }

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.author.isBot) {
            return
        }
        val message:Message = event.message
        val author:User = event.author
        println(message.contentRaw)
        if (message.contentRaw == ".invite") {
            message.reply(getInviteLink(event.jda)).queue()
        } else if (message.contentRaw == "coe") {
            author.openPrivateChannel().queue {
                    channel -> channel.sendMessage("salve").queue()
            }
        }
    }

    override fun onSlashCommand(event: SlashCommandEvent) {
        val member: Member? = event.member
        event.reply(getInviteLink(event.jda)).queue()
        if (event.name == "help") {
            TODO("Comando ainda não foi implementado")
        } else if (event.name == "ping") {
            TODO("Comando ainda não foi implementado")
        } else if (event.name == "invite") {
            event.reply(getInviteLink(event.jda)).queue()
        } else if (event.name == "create-ticket") {
            checkIfPrivateChannel(event)
            TODO("Comando ainda não foi implementado")
        } else if (event.name == "change-ticket") {
            checkIfPrivateChannel(event)
            TODO("Comando ainda não foi implementado")
        } else if (event.name == "archive-ticket") {
            checkIfPrivateChannel(event)
            TODO("Comando ainda não foi implementado")
        } else if (event.name == "archive-current-ticket") {
            checkIfPrivateChannel(event)
            TODO("Comando ainda não foi implementado")
        }
    }
}