package com.wallapop.marsrover.application.dto

import com.wallapop.marsrover.domain.MarsRoverAggregate

data class MarsRoverDTO(
    val coordinateDTO: CoordinateDTO,
    val z: String
)

fun MarsRoverAggregate.toMarsRoverDTO() = MarsRoverDTO(
    coordinateDTO = coordinate().toCoordinateDTO(),
    z= orientation().value
)