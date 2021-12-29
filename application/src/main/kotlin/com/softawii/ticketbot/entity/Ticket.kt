package com.softawii.ticketbot.entity

import javax.persistence.*

@Entity
@Table(name = "ticket")
class Ticket(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @ManyToOne
    var client: Client?,

    @OneToMany(mappedBy = "ticket", cascade = [CascadeType.MERGE], fetch = FetchType.LAZY)
    var messages: MutableList<Message> = ArrayList(),

    @Column
    var isArchived: Boolean = false
) {

    constructor() : this(null,null)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Ticket

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
