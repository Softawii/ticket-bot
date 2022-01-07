package com.softawii.ticketbot.entity

import javax.persistence.*

@Entity
@Table(name = "discordserver")
data class DiscordServer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column
    var serverId: Long? = null,

    @ElementCollection
    var categoryId: MutableSet<Long> = HashSet()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DiscordServer

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}