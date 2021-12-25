package service

import entity.Client
import entity.Ticket
import java.util.*
import kotlin.collections.ArrayList

object FakeDatabase:Database {
    private val tickets:MutableList<Ticket> = ArrayList()
    private val clients:MutableList<Client> = ArrayList()

    override fun findTicketById(ticketId:Long): Optional<Ticket> {
        val ticket = tickets.find { ticket -> ticket.id == ticketId }
        return Optional.ofNullable(ticket);
    }

    override fun findClientByDiscordId(discordId:Long): Optional<Client> {
        val client = clients.find { client -> client.discordId == discordId }
        return Optional.ofNullable(client);
    }

    @Throws(IllegalStateException::class)
    override fun saveClient(discordId: Long): Client {
        if (findClientByDiscordId(discordId).isPresent) {
            throw IllegalStateException("Usuário já está presente no banco de dados")
        } else {
            val client = Client(null, discordId, null)
            clients.add(client)
            client.id = clients.size - 1L
            println("Cliente ID ${client.id} criado")
            return client
        }
    }

    override fun createNewTicket(discordId: Long): Ticket {
        val clientOptional = findClientByDiscordId(discordId)
        val client = clientOptional.get()
        val ticket = Ticket(null, client)
        tickets.add(ticket)
        ticket.id = tickets.size - 1L
        client.activeTicket = ticket
        println("Ticket ID ${ticket.id} criado")
        return ticket
    }


}