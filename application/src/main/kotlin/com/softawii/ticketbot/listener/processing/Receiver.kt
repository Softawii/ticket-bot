package com.softawii.ticketbot.listener.processing

import com.softawii.ticketbot.entity.Ticket

interface Receiver {
    fun processMessage(ticketId:Long): Boolean
    fun unansweredByTeam(): List<Ticket>
}