package com.softawii.ticketbot.service

import com.softawii.ticketbot.entity.Client
import com.softawii.ticketbot.entity.DiscordServer
import com.softawii.ticketbot.entity.Ticket
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.Permission
import org.apache.logging.log4j.LogManager

object DiscordTicketService: TicketService {

    private val LOGGER = LogManager.getLogger(DiscordTicketService.javaClass)
    private val clientRepository = ClientRepository.getInstance()
    private val ticketRepository = TicketRepository.getInstance()
    private val discordServerRepository = DiscordServerRepository.getInstance()

    @JvmStatic
    fun getInviteLink(jda: JDA): String {
        val clientId = jda.selfUser.idLong
        val permission: Int = Permission.MESSAGE_SEND.offset
        return String.format(
            "https://discord.com/api/oauth2/authorize?client_id=%d&permissions=%d",
            clientId,
            permission
        ) + "&scope=bot%20applications.commands"
    }

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

    override fun createTicket(platformId: Long, serverId: Long): String {
        val clientOptional = clientRepository.findByDiscordId(platformId)
        val client: Client
        if (clientOptional.isEmpty) {
            client = clientRepository.createNewClient(Client(null, platformId, null, null))
            val ticket = client.activeTicket!!
            return "O seu novo ticket tem o ID: ${ticket.id}. Para enviar alguma mensagem para esse ticket utilize `/send-message ${ticket.id} MENSAGEM`"
        } else {
            client = clientOptional.get()
        }

        val serverOptional = discordServerRepository.findByServerId(serverId)
        if (serverOptional.isEmpty || serverOptional.get().categoryId == null) {
            return "O servidor ainda não foi configurado corretamente"
        }
        val ticket = ticketRepository.save(Ticket(id = null, client = client, discordServer = serverOptional.get()))
        client.activeTicket = ticket
        clientRepository.update(client)

        return "O seu novo ticket tem o ID: ${ticket.id}. Para enviar alguma mensagem para esse ticket utilize `/send-message ${ticket.id} MENSAGEM`"
    }

    override fun setupServer(serverId: Long): String {
        val discordServerOptional = discordServerRepository.findByServerId(serverId)
        if (discordServerOptional.isPresent) {
            if (discordServerOptional.get().categoryId == null) {
                return "O servidor já está em nossa base de dados mas ainda falta configurar a categoria do servidor"
            }
            return "O servidor já foi configurado anteriormente"
        }
        val discordServer = DiscordServer(null, serverId, null)
        discordServerRepository.save(discordServer)

        return "Servidor configurado com sucesso. Defina uma categoria para que os tickets possam ser criados"
    }
}