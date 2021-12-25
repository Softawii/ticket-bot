package listener.processing

import entity.Ticket

class DiscordReceiver:Receiver {
    override fun processMessage(ticketId: Long): Boolean {
        TODO("Not yet implemented")
    }

    override fun unansweredByTeam(): List<Ticket> {
        TODO("Not yet implemented")
    }
}