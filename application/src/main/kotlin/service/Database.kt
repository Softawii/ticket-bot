package service

import entity.Client
import entity.Ticket
import java.util.*

interface Database {
    fun findTicketById(ticketId:Long): Optional<Ticket>
    fun findClientByDiscordId(discordId:Long): Optional<Client>
    @Throws(IllegalStateException::class)
    fun saveClient(discordId: Long): Client
    fun createNewTicket(discordId:Long): Ticket
}