package entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "message")
class Message(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    var assignedId: Long?,
    var content: String?,
    var moment: LocalDateTime?,
    var type: MessageType?,
    @ManyToOne
    var ticket: Ticket?
) {
    constructor() : this(null,null,null,null,null, null)
}
