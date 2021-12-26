package service

import entity.Client
import org.hibernate.SessionFactory
import util.HibernateUtil
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


}