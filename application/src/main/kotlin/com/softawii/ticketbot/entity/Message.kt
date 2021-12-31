package com.softawii.ticketbot.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "message")
data class Message(
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Message

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
