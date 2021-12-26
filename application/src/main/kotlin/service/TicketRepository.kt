package service

import entity.Client
import entity.Ticket
import listener.MessageListener
import org.apache.logging.log4j.LogManager
import org.hibernate.SessionFactory
import util.HibernateUtil
import java.lang.RuntimeException
import java.util.*
import javax.persistence.NoResultException

class TicketRepository private constructor() : Repository<Ticket> {

    private var sessionFactory: SessionFactory = HibernateUtil.sessionFactory!!

    companion object {
        @Volatile
        private var INSTANCE: TicketRepository? = null
        private val LOGGER = LogManager.getLogger(MessageListener::class.java)

        fun getInstance(): TicketRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: TicketRepository().also { INSTANCE = it }
            }
    }

    override fun findById(id: Long): Optional<Ticket> {
        val entityManager = sessionFactory.createEntityManager()
        val query = entityManager.createQuery("SELECT t FROM Ticket t WHERE id = ?1").setMaxResults(1)
        query.setParameter(1, id)
        return Optional.ofNullable(query.singleResult as Ticket)
    }

    @Suppress("UNCHECKED_CAST")
    override fun findAll(): List<Ticket> {
        val entityManager = sessionFactory.createEntityManager()
        val query = entityManager.createQuery("SELECT t FROM Ticket t")
        return query.resultList as List<Ticket>
    }

    override fun save(entity: Ticket): Ticket {
        val entityManager = sessionFactory.createEntityManager()

        if (entity.client == null) {
            throw RuntimeException("Client is missing to create a ticket")
        } else if (entity.client!!.id == null) {
            throw RuntimeException("Client ID is missing to create a ticket")
        }

        entityManager.transaction.begin()
        entityManager.persist(entity)
        entityManager.flush()
        entityManager.transaction.commit()
        entityManager.close()
        return entity
    }

    override fun update(entity: Ticket): Ticket {
        TODO("Not yet implemented")
    }

}