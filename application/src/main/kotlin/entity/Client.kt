package entity

import javax.persistence.*

@Entity
@Table(name = "Client")
data class Client(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    var discordId:Long?,
    @OneToOne(cascade = [CascadeType.ALL])
    var activeTicket: Ticket?,
    @OneToMany(cascade = [CascadeType.ALL])
    var tickets: MutableList<Ticket>?
) {
    constructor() : this(null,null,null,null)
}