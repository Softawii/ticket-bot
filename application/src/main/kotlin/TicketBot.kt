import listener.MessageListener
import listener.ReadyListener
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.CommandData
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

            jda.upsertCommand("help", "Ajuda").queue();
            jda.upsertCommand("ping", "Pong").queue();
            jda.upsertCommand("invite", "Convite do bot").queue();
            val createNewTicket = CommandData("createticket", "Cria um novo ticket")
            jda.upsertCommand(createNewTicket).queue()
            val changeticket = CommandData("changeticket", "Troca para um ticket já existente")
            changeticket.addOption(OptionType.INTEGER, "ticket_id", "Ticket ID")
            jda.upsertCommand(changeticket).queue()
            jda.awaitReady()
        }

        private fun processPropertiesFile() {
            val reader = Files.newBufferedReader(Path.of("./application.properties"))
            reader.use {
                val propertiesFile = Properties()
                propertiesFile.load(reader)
                properties["discordToken"] = propertiesFile["discordToken"] as String
            }
        }
    }
}