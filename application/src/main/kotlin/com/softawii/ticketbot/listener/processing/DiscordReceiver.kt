package com.softawii.ticketbot.listener.processing

import com.softawii.ticketbot.entity.Ticket
import org.apache.logging.log4j.LogManager

class DiscordReceiver: Receiver {

    companion object {
        val LOGGER = LogManager.getLogger(Companion::class.java)
    }

    override fun processMessage(ticketId: Long): Boolean {
        TODO("Not yet implemented")
    }

    override fun unansweredByTeam(): List<Ticket> {
        TODO("Not yet implemented")
    }
}