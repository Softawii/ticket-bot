package listener.processing

import org.apache.logging.log4j.LogManager

class DiscordSender:Sender {

    companion object {
        val LOGGER = LogManager.getLogger(Companion::class.java)
    }

    override fun sendMessage(ticketId: Long, message: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun archiveMessage(ticketId: Long): Boolean {
        TODO("Not yet implemented")
    }
}