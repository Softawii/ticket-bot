package com.softawii.ticketbot.entity

import javax.persistence.*

@Entity
@Table(name = "client")
data class Client(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @Column
    var discordId:Long?,

    @OneToOne(cascade = [CascadeType.MERGE])
    var activeTicket: Ticket?,

    @OneToMany(mappedBy = "client", cascade = [CascadeType.MERGE], fetch = FetchType.LAZY)
    var tickets: MutableList<Ticket>?
) {
    constructor() : this(null,null,null,null)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Client

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}