package com.wallapop.marsrover.application.command

import com.wallapop.marsrover.application.dto.CoordinateDTO
import com.wallapop.marsrover.domain.CoordinateVO
import com.wallapop.marsrover.domain.MarsAggregate
import com.wallapop.marsrover.domain.repository.MarsRepository
import kotlin.jvm.Throws

data class CreateMarsCommand(
    val sizeX: Int,
    val sizeY: Int,
    val obstacle: List<CoordinateDTO>
)

class CreateMarsCommandHandler(
    private val marsRepository: MarsRepository
){

    @Throws(MarsAggregate.InvalidMarsException::class)
    fun dispatch(command: CreateMarsCommand){
        val mars = MarsAggregate(
            command.sizeX,
            command.sizeY,
            command.obstacle.map { coordinateDTO -> CoordinateVO(coordinateDTO.x,coordinateDTO.y) }
        )
        marsRepository.save(mars)
    }
}