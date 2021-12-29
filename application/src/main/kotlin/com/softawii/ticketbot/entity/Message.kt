package com.softawii.ticketbot.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "message")
class Message(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @Column
    var assignedId: Long?,

    @Column
    var content: String?,

    @Column
    var moment: LocalDateTime?,

    @Column
    var type: MessageType?,

    @ManyToOne
    var ticket: Ticket?
) {
    constructor() : this(null,null,null,null,null, null)
}
