package com.wallapop.marsrover.application.command

import com.wallapop.marsrover.application.dto.MarsRoverDTO
import com.wallapop.marsrover.domain.CoordinateVO
import com.wallapop.marsrover.domain.MarsRoverAggregate
import com.wallapop.marsrover.domain.OrientationVO
import com.wallapop.marsrover.domain.repository.MarsRoverRepository

data class SettleMarsRoverCommand(
    val marsRoverDTO: MarsRoverDTO
)

class SettleMarsRoverCommandHandler(
    private val marsRoverRepository: MarsRoverRepository
){

    fun dispatch(command: SettleMarsRoverCommand) {
        val marsRover = MarsRoverAggregate(
            CoordinateVO(command.marsRoverDTO.coordinateDTO.x,command.marsRoverDTO.coordinateDTO.y),
            OrientationVO(command.marsRoverDTO.z)
        )
        marsRoverRepository.save(marsRover)
    }

}