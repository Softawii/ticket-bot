package listener.processing

interface Sender {
    fun sendMessage(ticketId: Long, message: String): Boolean
    fun archiveMessage(ticketId: Long): Boolean
}