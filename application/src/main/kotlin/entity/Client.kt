package entity

import javax.persistence.*

@Entity
@Table(name = "client")
data class Client(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    var discordId:Long?,
    @OneToOne(cascade = [CascadeType.MERGE])
    var activeTicket: Ticket?,
    @OneToMany(cascade = [CascadeType.MERGE])
    var tickets: MutableList<Ticket>?
) {
    constructor() : this(null,null,null,null)
}