package com.softawii.ticketbot.annotation

import net.dv8tion.jda.api.interactions.commands.OptionType


@Target(AnnotationTarget.FUNCTION)
@java.lang.annotation.Repeatable(Arguments::class)
@kotlin.annotation.Repeatable()
annotation class Argument(
    val type: OptionType,
    val name: String,
    val description: String,
    val isRequired: Boolean = false
)
