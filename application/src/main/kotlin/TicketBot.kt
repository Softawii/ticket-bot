import annotation.Command
import listener.MessageListener
import listener.ReadyListener
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import org.apache.logging.log4j.LogManager
import org.reflections.Reflections
import org.reflections.scanners.Scanners
import org.reflections.util.ClasspathHelper
import org.reflections.util.ConfigurationBuilder
import org.reflections.util.FilterBuilder
import util.PropertiesUtil

class TicketBot {
    companion object {
        val LOGGER = LogManager.getLogger(Companion::class.java)

        private fun initAvailableCommands(pkgName: String) {
            val reflections = Reflections(
                ConfigurationBuilder()
                    .filterInputsBy(FilterBuilder().includePackage(pkgName))
                    .setUrls(ClasspathHelper.forPackage(pkgName))
                    .setScanners(Scanners.SubTypes.filterResultsBy { _ -> true})
            )

            reflections.getSubTypesOf(Object::class.java).forEach {
                LOGGER.debug("Found class: ${it.name} | Methods: ${it.methods}")
                for (method in it.methods) {
                    if(method.isAnnotationPresent(Command::class.java)) {
                        LOGGER.debug("Adding command list: ${method.name}")
                        MessageListener.COMMANDS[method.name.lowercase()] = method
                    }
                }
            }
        }

        fun start() {
            initAvailableCommands("command")
            startJDA()
        }

        private fun startJDA() {
            val jda = JDABuilder.createDefault(PropertiesUtil.properties["discord.token"] as String?)
                .addEventListeners(ReadyListener())
                .addEventListeners(MessageListener())
                .build()

            val ticketId = OptionData(OptionType.INTEGER, "ticket-id", "Ticket ID", true)
            jda.updateCommands()
                .addCommands(CommandData("help", "Ajuda"))
                .addCommands(CommandData("ping", "Pong"))
                .addCommands(CommandData("invite", "Convite do bot"))
                .addCommands(CommandData("create-ticket", "Cria um novo ticket"))
                .addCommands(CommandData("change-ticket", "Troca para um ticket já existente")
                    .addOptions(ticketId))
                .addCommands(CommandData("archive-ticket", "Arquiva um ticket já existente")
                    .addOptions(ticketId))
                .addCommands(CommandData("archive-current-ticket", "Arquiva um ticket já existente"))
                .addCommands(CommandData("send-message", "Envia uma mensagem para um ticket")
                    .addOptions(ticketId))
                .addCommands(CommandData("active-ticket", "Informa o ticket ativo"))
            .queue()

            jda.awaitReady()
        }
    }
}