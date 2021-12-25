package service

import entity.Message
import org.hibernate.SessionFactory
import util.HibernateUtil
import java.util.*
import javax.persistence.EntityManagerFactory

class MessageRepository private constructor(entityFactory: EntityManagerFactory) : Repository<Message> {

    private var sessionFactory: SessionFactory = HibernateUtil.sessionFactory!!

    companion object {
        @Volatile
        private var INSTANCE: MessageRepository? = null

        fun getInstance(entityFactory: EntityManagerFactory): MessageRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: MessageRepository(entityFactory).also { INSTANCE = it }
            }
    }

    override fun findById(id: Long): Optional<Message> {
        val entityManager = sessionFactory.createEntityManager()
        val query = entityManager.createQuery("SELECT c FROM Client c WHERE id = ?1").setMaxResults(1)
        query.setParameter(1, id)
        return Optional.ofNullable(query.singleResult as Message)
    }

    override fun findAll(): List<Message> {
        val entityManager = sessionFactory.createEntityManager()
        val query = entityManager.createQuery("SELECT c FROM Client c")
        return query.resultList as List<Message>
    }

    override fun save(entity: Message): Message {
        val entityManager = sessionFactory.createEntityManager()
        entityManager.transaction.begin();
        entityManager.persist(entity);
        entityManager.flush();
        entityManager.transaction.commit();
        entityManager.close();
        return entity
    }

}