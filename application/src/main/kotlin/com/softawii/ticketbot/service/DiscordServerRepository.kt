package com.softawii.ticketbot.service

import com.softawii.ticketbot.entity.DiscordServer
import com.softawii.ticketbot.util.HibernateUtil
import org.hibernate.SessionFactory
import java.util.*
import javax.persistence.NoResultException

class DiscordServerRepository : Repository<DiscordServer> {

    private var sessionFactory: SessionFactory = HibernateUtil.sessionFactory!!

    companion object {
        @Volatile
        private var INSTANCE: DiscordServerRepository? = null

        fun getInstance(): DiscordServerRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: DiscordServerRepository().also { INSTANCE = it }
            }
    }

    override fun findById(id: Long): Optional<DiscordServer> {
        val entityManager = sessionFactory.createEntityManager()
        val query = entityManager.createQuery("SELECT d FROM DiscordServer d WHERE id = ?1").setMaxResults(1)
        query.setParameter(1, id)
        return Optional.ofNullable(query.singleResult as DiscordServer)
    }

    @Suppress("UNCHECKED_CAST")
    override fun findAll(): List<DiscordServer> {
        val entityManager = sessionFactory.createEntityManager()
        val query = entityManager.createQuery("SELECT d FROM DiscordServer d")
        return query.resultList as List<DiscordServer>
    }

    override fun save(entity: DiscordServer): DiscordServer {
        val session = sessionFactory.currentSession
        session.transaction.begin()
        val entityId = session.save(entity) as Long
        session.flush()
        session.transaction.commit()
        session.close()
        entity.id = entityId

        return entity
    }

    override fun update(entity: DiscordServer): DiscordServer {
        val session = sessionFactory.currentSession
        session.transaction.begin()

        val mergedEntity = session.merge(entity) as DiscordServer

        session.flush()
        session.transaction.commit()
        session.close()

        return mergedEntity
    }

    fun findByServerId(serverId: Long): Optional<DiscordServer> {
        return try {
            val entityManager = sessionFactory.createEntityManager()
            val query = entityManager.createQuery("SELECT d FROM DiscordServer d WHERE serverId = ?1").setMaxResults(1)
            query.setParameter(1, serverId)
            Optional.ofNullable(query.singleResult as DiscordServer)
        } catch (e: NoResultException) {
            Optional.empty()
        }
    }


}