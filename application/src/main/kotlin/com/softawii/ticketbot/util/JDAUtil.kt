package com.softawii.ticketbot.util

import com.softawii.ticketbot.annotation.Argument
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData

class JDAUtil {
    companion object {
        fun getOptionData(type: OptionType, name: String, description: String, isRequired: Boolean): OptionData {
            return OptionData(type, name, description, isRequired)
        }
        fun getOptionData(argument: Argument): OptionData {
            return OptionData(argument.type, argument.name, argument.description, argument.isRequired)
        }
    }
}