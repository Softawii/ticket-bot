package entity

data class Ticket(
    val id: Long,
    val clientId: Long,
    val channelId: Long,
    val serverId: Long,
    val messages: List<Message>
)
