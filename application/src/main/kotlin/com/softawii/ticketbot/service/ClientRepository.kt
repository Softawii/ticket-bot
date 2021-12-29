package com.softawii.ticketbot.service

import com.softawii.ticketbot.entity.Client
import com.softawii.ticketbot.entity.Ticket
import org.hibernate.SessionFactory
import com.softawii.ticketbot.util.HibernateUtil
import java.util.*
import javax.persistence.*

class ClientRepository private constructor() : Repository<Client> {

    private var sessionFactory: SessionFactory = HibernateUtil.sessionFactory!!

    companion object {
        @Volatile
        private var INSTANCE: ClientRepository? = null

        fun getInstance(): ClientRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ClientRepository().also { INSTANCE = it }
            }
    }

    override fun findById(id: Long): Optional<Client> {
        val entityManager = sessionFactory.createEntityManager()
        val query = entityManager.createQuery("SELECT c FROM Client c WHERE id = ?1").setMaxResults(1)
        query.setParameter(1, id)
        return Optional.ofNullable(query.singleResult as Client)
    }

    @Suppress("UNCHECKED_CAST")
    override fun findAll(): List<Client> {
        val entityManager = sessionFactory.createEntityManager()
        val query = entityManager.createQuery("SELECT c FROM Client c")
        return query.resultList as List<Client>
    }

    override fun save(entity: Client): Client {
        val session = sessionFactory.currentSession
        session.transaction.begin()
        val entityId = session.save(entity) as Long
        session.flush()
        session.transaction.commit()
        session.close()
        entity.id = entityId

        return entity
    }

    override fun update(entity: Client): Client {
        val session = sessionFactory.currentSession
        session.transaction.begin()

        val mergedEntity = session.merge(entity) as Client

        session.flush()
        session.transaction.commit()
        session.close()

        return mergedEntity
    }


    fun findByDiscordId(discordId: Long): Optional<Client> {
        return try {
            val entityManager = sessionFactory.createEntityManager()
            val query = entityManager.createQuery("SELECT c FROM Client c WHERE discordId = ?1").setMaxResults(1)
            query.setParameter(1, discordId)
            Optional.ofNullable(query.singleResult as Client)
        } catch (e: NoResultException) {
            Optional.empty()
        }
    }

    /**
     * Create a new client and a ticket in the same transaction
     */
    fun createNewClient(entity: Client): Client {
        val session = sessionFactory.currentSession
        session.transaction.begin()

        val ticket = Ticket(null, entity)
        entity.activeTicket = ticket
        val entityId = session.save(entity) as Long
        val ticketId = session.save(ticket) as Long
        entity.id = entityId
        ticket.id = ticketId

        session.flush()
        session.transaction.commit()
        session.close()
        return entity
    }


}