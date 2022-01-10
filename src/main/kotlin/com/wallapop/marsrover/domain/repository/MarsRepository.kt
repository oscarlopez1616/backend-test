package com.wallapop.marsrover.domain.repository

import com.wallapop.marsrover.domain.AggregateNotFoundException
import com.wallapop.marsrover.domain.MarsAggregate
import kotlin.jvm.Throws

interface MarsRepository {
    @Throws(AggregateNotFoundException::class)
    fun loadOrFail(): MarsAggregate
    fun save(mars:MarsAggregate)
}