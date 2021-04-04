package com.simplechat.core.domain

interface EntityMapper<Entity, Domain> {

    fun toEntity(e: Domain): Entity
    fun toDomain(e: Entity): Domain

    fun toEntities(domains: List<Domain>): List<Entity> {
        return domains.map {
            toEntity(it)
        }
    }

    fun toDomains(entities: List<Entity>): List<Domain> {
        return entities.map {
            toDomain(it)
        }
    }

}