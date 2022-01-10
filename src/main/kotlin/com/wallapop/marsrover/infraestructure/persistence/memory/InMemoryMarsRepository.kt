package com.wallapop.marsrover.infraestructure.persistence.memory

import com.wallapop.marsrover.domain.AggregateNotFoundException
import com.wallapop.marsrover.domain.MarsAggregate
import com.wallapop.marsrover.domain.repository.MarsRepository

class InMemoryMarsRepository: MarsRepository {
    private var currentMars:MarsAggregate? = null

    override fun loadOrFail(): MarsAggregate {
        if(currentMars==null) throw AggregateNotFoundException("it does not exist planet mars")
        return currentMars as MarsAggregate
    }

    override fun save(mars: MarsAggregate) {
        currentMars = mars
    }
}