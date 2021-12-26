package service

import java.util.*

interface Repository<T> {
    fun findById(id:Long): Optional<T>
    fun findAll(): List<T>
    fun save(entity:T) : T
    fun update(entity:T) : T
}