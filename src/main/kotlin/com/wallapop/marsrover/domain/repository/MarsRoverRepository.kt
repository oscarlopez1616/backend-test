package com.wallapop.marsrover.domain.repository

import com.wallapop.marsrover.domain.AggregateNotFoundException
import com.wallapop.marsrover.domain.MarsRoverAggregate
import kotlin.jvm.Throws

interface MarsRoverRepository {
    @Throws(AggregateNotFoundException::class)
    fun loadOrFail(): MarsRoverAggregate
    fun save(marsRover:MarsRoverAggregate)
}