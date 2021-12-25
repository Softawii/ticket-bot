package service

import org.apache.logging.log4j.LogManager

object DiscordTicketService:TicketService {

    val LOGGER = LogManager.getLogger(DiscordTicketService.javaClass)

    private val database: Database = FakeDatabase

    override fun archiveTicket(userId: Long, ticketId: Long): String {
        val ticketOptional = database.findTicketById(ticketId)
        if (ticketOptional.isEmpty || (ticketOptional.get().client.discordId != userId)) {
            return "Esse ticket não existe ou não é seu ticket"
        } else {
            return "Ticket de ID ${ticketOptional.get().id} arquivado com sucesso"
        }
    }

    override fun archiveCurrentTicket(userId: Long): String {
        val clientOptional = database.findClientByDiscordId(userId)
        if (clientOptional.isEmpty) {
            return "Você ainda não está cadastrado. Crie um ticket antes"
        } else {
            val client = clientOptional.get()
            if (client.activeTicket == null) {
                return "Você não tem ticket ativo para poder ser arquivado"
            } else {
                val ticket = client.activeTicket!!
                client.activeTicket = null
                return "Ticket ativo (${ticket.id}) arquivado com sucesso"
            }
        }
    }

    override fun activeTicket(userId: Long): String {
        val clientOptional = database.findClientByDiscordId(userId)
        if (clientOptional.isEmpty) {
            return "Você ainda não está cadastrado. Crie um ticket antes"
        } else {
            val client = clientOptional.get()
            return if (client.activeTicket == null) {
                "Você não tem tickets ativos"
            } else {
                "Seu ticket ativo é o de ID ${client.activeTicket!!.id}"
            }
        }
    }

    override fun createTicket(userId: Long): String {
        val clientOptional = database.findClientByDiscordId(userId)
        if (clientOptional.isEmpty) {
            database.saveClient(userId)
        }
        val ticket = database.createNewTicket(userId)
        return "O seu novo ticket tem o ID: ${ticket.id}. Para enviar alguma mensagem para esse ticket utilize `/send-message ${ticket.id} MENSAGEM`"
    }

    override fun createUser(userId: Long): String {
        TODO()
    }
}