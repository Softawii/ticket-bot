import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.hooks.EventListener


class ReadyListener: EventListener {

    companion object {
        fun getInviteLink(jda: JDA): String {
            val clientId = jda.selfUser.idLong
            val permission: Int = Permission.MESSAGE_SEND.offset
            return String.format(
                "https://discord.com/api/oauth2/authorize?client_id=%d&permissions=%d",
                clientId,
                permission
            ) + "&scope=bot%20applications.commands"
        }
    }

    override fun onEvent(event: GenericEvent) {
        if (event is ReadyEvent) {
            println("API is ready!\n"+getInviteLink(event.jda))
        }
    }
}