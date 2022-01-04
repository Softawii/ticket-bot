package com.softawii.ticketbot

import com.softawii.ticketbot.annotation.Argument
import com.softawii.ticketbot.annotation.Command
import com.softawii.ticketbot.internal.CommandHandler
import com.softawii.ticketbot.listener.MessageListener
import com.softawii.ticketbot.listener.ReadyListener
import com.softawii.ticketbot.util.JDAUtil.Companion.getOptionData
import com.softawii.ticketbot.util.PropertiesUtil
import com.softawii.ticketbot.util.ReflectionUtil
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import org.apache.logging.log4j.LogManager
import java.lang.reflect.Method
import kotlin.system.exitProcess

class TicketBot {
    companion object {
        val LOGGER = LogManager.getLogger(Companion::class.java)
        private lateinit var arguments: Set<String>

        private fun initAvailableCommands(pkgName: String, jda: JDA) {
            val commandClasses = ReflectionUtil.getClassesByPackageName(pkgName)

            val commands = ArrayList<CommandData>()
            commandClasses.forEach {
                LOGGER.debug("Found class: ${it.name}")
                for (method in it.methods) {
                    if (method.isAnnotationPresent(Command::class.java)) {
                        LOGGER.debug("Adding command list: ${method.name}")
                        val commandAnnotation =
                            method.declaredAnnotations.firstOrNull { annotation -> annotation is Command } as Command
                        val commandArguments = method.declaredAnnotations.filterIsInstance<Argument>()
                        val (commandName: String, commandData: CommandData) = processCommandAnnotations(
                            commandArguments,
                            commandAnnotation,
                            method
                        )

                        if (MessageListener.COMMANDS.containsKey(commandName)) {
                            LOGGER.fatal("Command $commandName is duplicated." +
                                    "Found in classes: ${MessageListener.COMMANDS[commandName]!!.getMethodPath()} and ${method.declaringClass.name}.${method.name}()")
                            exitProcess(-1)
                        }
                        commands.add(commandData)


                        MessageListener.COMMANDS[commandName] = CommandHandler(commandAnnotation.permissions.asList(), method, commandAnnotation.environment)
                    }
                }
                jda.updateCommands().addCommands(commands).queue()
            }
        }

        private fun processCommandAnnotations(
            commandArguments: List<Argument>,
            commandAnnotation: Command,
            method: Method
        ): Pair<String, CommandData> {
            val optionDatas = ArrayList<OptionData>()

            if (commandArguments.isNotEmpty()) {
                for (argument in commandArguments) {
                    val optionData = getOptionData(argument)
                    optionDatas.add(optionData)
                }
            }

            val commandName: String = commandAnnotation.name.lowercase().ifEmpty {
                method.name.lowercase()
            }
            val commandDescription = commandAnnotation.description
            val commandData = CommandData(commandName, commandDescription)
            commandData.addOptions(optionDatas)
            return Pair(commandName, commandData)
        }

        fun start(args: Array<String>) {
            parseArguments(args)
            startJDA()
        }

        private fun parseArguments(args: Array<String>) {
            arguments = HashSet(args.asList())
        }

        fun containsArgument(arg: String): Boolean {
            return arguments.contains(arg)
        }

        fun isDeveloperModeEnabled(): Boolean {
            return arguments.contains("dev")
        }

        private fun startJDA() {
            val jda = JDABuilder.createDefault(PropertiesUtil.properties["discord.token"] as String?)
                .addEventListeners(ReadyListener())
                .addEventListeners(MessageListener())
                .build()

            initAvailableCommands("com.softawii.ticketbot.command", jda)

            jda.awaitReady()
        }
    }
}