package com.softawii.ticketbot.annotation

import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData

@Target(AnnotationTarget.FUNCTION)
annotation class Argument(
    val type: OptionType,
    val name: String,
    val description: String,
    val isRequired: Boolean = false
)
