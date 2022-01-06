package com.softawii.ticketbot.entity

import javax.persistence.*

@Entity
@Table(name = "server")
data class Server(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        var id: Long? = null,

        @Column
        var discordID:Long?,

        @Column
        var supportCategories: Long?
) {
    constructor() : this(null,null,null)

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