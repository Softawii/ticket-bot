package com.softawii.ticketbot.util

import com.softawii.ticketbot.entity.Client
import com.softawii.ticketbot.entity.Message
import com.softawii.ticketbot.entity.Ticket
import org.hibernate.SessionFactory
import org.hibernate.boot.registry.StandardServiceRegistry
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import org.hibernate.cfg.Configuration

class HibernateUtil private constructor() {

    companion object {
        private var instance: HibernateUtil? = null

        private fun init(): HibernateUtil {
            val configuration = Configuration()
            configuration.configure()
                .addAnnotatedClass(Client::class.java)
                .addAnnotatedClass(Message::class.java)
                .addAnnotatedClass(Ticket::class.java)
                .addProperties(PropertiesUtil.properties)
            serviceRegistry = StandardServiceRegistryBuilder().applySettings(configuration.properties).build()
            sessionFactory = configuration.buildSessionFactory(serviceRegistry)
            return HibernateUtil()
        }

        @get:Synchronized
        var sessionFactory: SessionFactory? = null
            get() {
                if (instance == null) {
                    instance = init()
                }
                return field
            }
            private set
        private var serviceRegistry: StandardServiceRegistry? = null
    }
}