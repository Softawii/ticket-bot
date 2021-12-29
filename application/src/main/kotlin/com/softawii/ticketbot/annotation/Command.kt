package com.softawii.ticketbot.annotation

import com.softawii.ticketbot.internal.Environment
import com.softawii.ticketbot.internal.Environment.SERVER
import net.dv8tion.jda.api.Permission

@Target(AnnotationTarget.FUNCTION)
annotation class Command(
    val name: String = "",
    val environment: Environment = SERVER,
    val description: String,
    val permissions: Array<Permission> = []
)