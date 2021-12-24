import listener.MessageListener
import listener.ReadyListener
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

class TicketBot {
    companion object {
        private var properties: MutableMap<String, String> = HashMap()
        fun start() {
            processPropertiesFile()
            startJDA()
        }

        private fun startJDA() {
            val jda = JDABuilder.createDefault(properties["discordToken"])
                .addEventListeners(ReadyListener())
                .addEventListeners(MessageListener())
                .build()

            val ticketId = OptionData(OptionType.INTEGER, "ticket-id", "Ticket ID")
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
            .queue()

            jda.awaitReady()
        }

        private fun processPropertiesFile() {
            val reader = Files.newBufferedReader(Path.of("./application.properties"))
            reader.use {
                val propertiesFile = Properties()
                propertiesFile.load(reader)
                properties["discordToken"] = propertiesFile["discord.token"] as String
            }
        }
    }
}