package service

interface TicketService {
    fun archiveTicket(userId: Long, ticketId: Long): String
    fun archiveCurrentTicket(userId: Long): String
    fun activeTicket(userId: Long): String
    fun createTicket(userId: Long): String
    fun createUser(userId: Long): String
}