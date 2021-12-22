import ReadyListener.Companion.getInviteLink
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class MessageListener: ListenerAdapter() {

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
        if (event.name == "invite") {
            event.reply(getInviteLink(event.jda)).queue()
        }
    }
}