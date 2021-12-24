package entity

data class Client(
    val id:Long,
    val discordId:Long,
    val activeTicket:Ticket
)