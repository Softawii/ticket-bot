package com.softawii.ticketbot.entity

import javax.persistence.*

@Entity
@Table(name = "ticket")
data class Ticket(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    var client: Client? = null,

    @OneToMany(mappedBy = "ticket", cascade = [CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.DETACH], fetch = FetchType.LAZY)
    var messages: MutableList<Message> = ArrayList(),

    @Column
    var isArchived: Boolean = false,

    @OneToOne(cascade = [CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.DETACH])
    var discordServer: DiscordServer? = null
) {

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
