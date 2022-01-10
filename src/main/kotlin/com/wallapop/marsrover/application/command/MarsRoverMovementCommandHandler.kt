package com.wallapop.marsrover.application.command

import com.wallapop.marsrover.domain.MarsAggregate
import com.wallapop.marsrover.domain.MarsRoverAggregate
import com.wallapop.marsrover.domain.repository.MarsRepository
import com.wallapop.marsrover.domain.repository.MarsRoverRepository

data class MarsRoverMovementCommand(
    val marsRoverMovement: String
)

class MarsRoverMovementCommandHandler(
    private val marsRepository: MarsRepository,
    private val marsRoverRepository: MarsRoverRepository
){

    fun dispatch(command: MarsRoverMovementCommand) {
        val marsRover = marsRoverRepository.loadOrFail()
        val mars = marsRepository.loadOrFail()
        executeCommand(command.marsRoverMovement,mars,marsRover)
    }

    private fun executeCommand(command: String, mars: MarsAggregate, marsRover:MarsRoverAggregate) {
        if (command == "f") {
            marsRover.moveForward(mars)
            return
        }
        if (command == "b") {
            marsRover.moveBackWard(mars)
            return
        }
        if (command == "l") {
            marsRover.rotateLeft()
            return
        }
        if (command == "r") {
            marsRover.rotateRight()
            return
        }
        throw MarsRoverInvalidMovementException("Invalid movement: $command")
    }

    class MarsRoverInvalidMovementException(message: String) : RuntimeException(message)
}