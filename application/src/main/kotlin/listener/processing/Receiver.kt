package listener.processing

import entity.Ticket

interface Receiver {
    fun processMessage(ticketId:Long): Boolean
    fun unansweredByTeam(): List<Ticket>
}