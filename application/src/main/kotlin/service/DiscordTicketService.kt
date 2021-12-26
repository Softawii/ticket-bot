package service

import entity.Client
import entity.Ticket
import org.apache.logging.log4j.LogManager

object DiscordTicketService:TicketService {

    private val LOGGER = LogManager.getLogger(DiscordTicketService.javaClass)
    private val clientRepository = ClientRepository.getInstance()
    private val ticketRepository = TicketRepository.getInstance()

    override fun archiveTicket(platformId: Long, ticketId: Long): String {
//        val ticketOptional = database.findTicketById(ticketId)
//        if (ticketOptional.isEmpty || (ticketOptional.get().client!!.discordId != platformId)) {
//            return "Esse ticket não existe ou não é seu ticket"
//        } else {
//            return "Ticket de ID ${ticketOptional.get().id} arquivado com sucesso"
//        }
        TODO()
    }

    override fun archiveCurrentTicket(platformId: Long): String {
//        val clientOptional = database.findClientByDiscordId(platformId)
//        if (clientOptional.isEmpty) {
//            return "Você ainda não está cadastrado. Crie um ticket antes"
//        } else {
//            val client = clientOptional.get()
//            if (client.activeTicket == null) {
//                return "Você não tem ticket ativo para poder ser arquivado"
//            } else {
//                val ticket = client.activeTicket!!
//                client.activeTicket = null
//                return "Ticket ativo (${ticket.id}) arquivado com sucesso"
//            }
//        }
        TODO()
    }

    override fun activeTicket(platformId: Long): String {
//        val clientOptional = database.findClientByDiscordId(platformId)
//        if (clientOptional.isEmpty) {
//            return "Você ainda não está cadastrado. Crie um ticket antes"
//        } else {
//            val client = clientOptional.get()
//            return if (client.activeTicket == null) {
//                "Você não tem tickets ativos"
//            } else {
//                "Seu ticket ativo é o de ID ${client.activeTicket!!.id}"
//            }
//        }
        TODO()
    }

    override fun createTicket(platformId: Long): String {
        val clientOptional = clientRepository.findByDiscordId(platformId)
        val client: Client
        if (clientOptional.isEmpty) {
            client = clientRepository.save(Client(null, platformId, null, null))
        } else {
            client = clientOptional.get()
        }
        val ticket = ticketRepository.save(Ticket(null, client))

        // After create the ticket we will set it as the current
        // TODO: Criar cliente e ticket na mesma transação

        client.activeTicket = ticket
        clientRepository.update(client)

        return "O seu novo ticket tem o ID: ${ticket.id}. Para enviar alguma mensagem para esse ticket utilize `/send-message ${ticket.id} MENSAGEM`"
    }
}