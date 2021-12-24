package entity

import java.time.LocalDateTime

data class Message(
    val assignedId: Long,
    val content: String,
    val moment: LocalDateTime,
    val type: MessageType
)