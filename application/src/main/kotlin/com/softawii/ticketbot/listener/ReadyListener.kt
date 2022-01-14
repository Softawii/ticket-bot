package com.softawii.ticketbot.listener

import com.softawii.ticketbot.TicketBot
import com.softawii.ticketbot.service.DiscordService
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.hooks.EventListener
import org.apache.logging.log4j.LogManager


class ReadyListener: EventListener {

    companion object {
        val LOGGER = LogManager.getLogger(Companion::class.java)
    }

    override fun onEvent(event: GenericEvent) {
        if (event is ReadyEvent) {
            println(
                "████████╗██╗ ██████╗██╗  ██╗███████╗████████╗    ██████╗  ██████╗ ████████╗\n" +
                        "╚══██╔══╝██║██╔════╝██║ ██╔╝██╔════╝╚══██╔══╝    ██╔══██╗██╔═══██╗╚══██╔══╝\n" +
                        "   ██║   ██║██║     █████╔╝ █████╗     ██║       ██████╔╝██║   ██║   ██║   \n" +
                        "   ██║   ██║██║     ██╔═██╗ ██╔══╝     ██║       ██╔══██╗██║   ██║   ██║   \n" +
                        "   ██║   ██║╚██████╗██║  ██╗███████╗   ██║       ██████╔╝╚██████╔╝   ██║   \n" +
                        "   ╚═╝   ╚═╝ ╚═════╝╚═╝  ╚═╝╚══════╝   ╚═╝       ╚═════╝  ╚═════╝    ╚═╝   \n" +
                        "API is ready! Invite link: " + DiscordService.getInviteLink(event.jda)
            )
            if (TicketBot.isDeveloperModeEnabled()) {
                val guilds = event.jda.guilds
                guilds.forEach { DiscordService.setupServer(it.idLong) }
                LOGGER.debug("[DEV-ONLY] Setting up joined servers")
            }
        }
    }
}