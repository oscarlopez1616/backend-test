package com.wallapop.marsrover.infraestructure.persistence.memory

import com.wallapop.marsrover.domain.AggregateNotFoundException
import com.wallapop.marsrover.domain.MarsRoverAggregate
import com.wallapop.marsrover.domain.repository.MarsRoverRepository

class InMemoryMarsRoverRepository: MarsRoverRepository {
    private var currentMarsRover: MarsRoverAggregate? = null

    override fun loadOrFail(): MarsRoverAggregate {
        if(currentMarsRover==null) throw AggregateNotFoundException("it does not exist marsRover")
        return currentMarsRover as MarsRoverAggregate
    }

    override fun save(marsRover: MarsRoverAggregate) {
        currentMarsRover = marsRover
    }
}