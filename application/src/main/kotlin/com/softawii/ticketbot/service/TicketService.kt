package com.softawii.ticketbot.service

interface TicketService {
    fun archiveTicket(platformId: Long, ticketId: Long): String
    fun archiveCurrentTicket(platformId: Long): String
    fun activeTicket(platformId: Long): String
    fun createTicket(platformId: Long): String
}