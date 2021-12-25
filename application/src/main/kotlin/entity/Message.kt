package entity

import java.time.LocalDateTime

class Message(
    var assignedId: Long,
    var content: String,
    var moment: LocalDateTime,
    var type: MessageType
)