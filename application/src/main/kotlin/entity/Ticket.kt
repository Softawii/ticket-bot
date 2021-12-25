package entity

class Ticket(
    var id: Long?,
    var client: Client,
    var messages: MutableList<Message> = ArrayList(),
    var isArchived: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Ticket

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
