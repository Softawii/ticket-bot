package com.softawii.ticketbot.service

import com.softawii.ticketbot.entity.Client
import com.softawii.ticketbot.entity.Ticket
import org.apache.logging.log4j.LogManager

object DiscordTicketService: TicketService {

    private val LOGGER = LogManager.getLogger(DiscordTicketService.javaClass)
    private val clientRepository = ClientRepository.getInstance()
    private val ticketRepository = TicketRepository.getInstance()

    override fun archiveTicket(platformId: Long, ticketId: Long): String {
        val ticketOptional = ticketRepository.findById(ticketId)
        if (ticketOptional.isEmpty || (ticketOptional.get().client!!.discordId != platformId)) {
            return "Esse ticket não existe ou não é seu ticket"
        } else {
            val ticket = ticketOptional.get()
            ticket.isArchived = true
            ticketRepository.update(ticket)
            if (ticket.client!!.activeTicket != null &&
                ticket.client!!.activeTicket!!.id == ticket.id) {
                val client = ticket.client!!
                client.activeTicket = null
                clientRepository.update(ticket.client!!)
            }
            return "Ticket de ID ${ticketOptional.get().id} arquivado com sucesso"
        }
    }

    override fun archiveCurrentTicket(platformId: Long): String {
        val clientOptional = clientRepository.findByDiscordId(platformId)
        if (clientOptional.isEmpty) {
            return "Você ainda não está cadastrado. Crie um ticket antes"
        } else {
            val client = clientOptional.get()
            if (client.activeTicket == null) {
                return "Você não tem ticket ativo para poder ser arquivado"
            } else {
                val ticket = client.activeTicket!!
                client.activeTicket = null
                ticket.isArchived = true
                ticketRepository.update(ticket)
                clientRepository.update(client)
                return "Ticket (${ticket.id}) arquivado com sucesso"
            }
        }
    }

    override fun activeTicket(platformId: Long): String {
        val clientOptional = clientRepository.findByDiscordId(platformId)
        if (clientOptional.isEmpty) {
            return "Você ainda não está cadastrado. Crie um ticket antes"
        } else {
            val client = clientOptional.get()
            if (client.activeTicket == null) {
                return "Você não tem tickets ativos"
            } else {
                return "Seu ticket ativo é o de ID ${client.activeTicket!!.id}"
            }
        }
    }

    override fun createTicket(platformId: Long): String {
        val clientOptional = clientRepository.findByDiscordId(platformId)
        val client: Client
        if (clientOptional.isEmpty) {
            client = clientRepository.createNewClient(Client(null, platformId, null, null))
            val ticket = client.activeTicket!!
            return "O seu novo ticket tem o ID: ${ticket.id}. Para enviar alguma mensagem para esse ticket utilize `/send-message ${ticket.id} MENSAGEM`"
        } else {
            client = clientOptional.get()
        }

        val ticket = ticketRepository.save(Ticket(null, client))
        client.activeTicket = ticket
        clientRepository.update(client)

        return "O seu novo ticket tem o ID: ${ticket.id}. Para enviar alguma mensagem para esse ticket utilize `/send-message ${ticket.id} MENSAGEM`"
    }
}