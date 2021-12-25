package service

import entity.Ticket
import org.hibernate.SessionFactory
import util.HibernateUtil
import java.util.*

class TicketRepository private constructor() : Repository<Ticket> {

    private var sessionFactory: SessionFactory = HibernateUtil.sessionFactory!!

    companion object {
        @Volatile
        private var INSTANCE: TicketRepository? = null

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

    override fun findAll(): List<Ticket> {
        val entityManager = sessionFactory.createEntityManager()
        val query = entityManager.createQuery("SELECT t FROM Ticket t")
        return query.resultList as List<Ticket>
    }

    override fun save(entity: Ticket): Ticket {
        val entityManager = sessionFactory.createEntityManager()
        entityManager.transaction.begin();
        entityManager.persist(entity);
        entityManager.flush();
        entityManager.transaction.commit();
        entityManager.close();
        return entity
    }

}