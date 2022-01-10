package com.wallapop.marsrover.infraestructure.cli.controller

import com.wallapop.marsrover.application.command.CreateMarsCommandHandler
import com.wallapop.marsrover.application.command.MarsRoverMovementCommandHandler
import com.wallapop.marsrover.application.command.SettleMarsRoverCommandHandler
import com.wallapop.marsrover.application.query.RetrieveMarsRoverInfoQueryHandler
import com.wallapop.marsrover.infraestructure.persistence.memory.InMemoryMarsRepository
import com.wallapop.marsrover.infraestructure.persistence.memory.InMemoryMarsRoverRepository
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.util.*


@RunWith(MockitoJUnitRunner::class)
class MarsRoverControllerE2E{


    @Test
    fun `should work properly`(){
        val marsRepository = InMemoryMarsRepository()
        val marsRoverRepository = InMemoryMarsRoverRepository()

        val `in` = ByteArrayInputStream("3 3 1 1 n f f b f r f f l f b r f f f f".toByteArray())
        System.setIn(`in`)

        val outputStreamCaptor = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStreamCaptor))

        val marsRoverController = MarsRoverController(
            Scanner(System.`in`),
            CreateMarsCommandHandler(marsRepository),
            SettleMarsRoverCommandHandler(marsRoverRepository),
            MarsRoverMovementCommandHandler(marsRepository,marsRoverRepository),
            RetrieveMarsRoverInfoQueryHandler(marsRoverRepository)
        )
        assertThrows<NoSuchElementException> {
            marsRoverController.execute()
        }

        Assertions.assertEquals("Insert horizontal map size:\n" +
                "Insert vertical map size:\n" +
                "Insert horizontal initial rover position:\n" +
                "Insert vertical initial rover position:\n" +
                "Insert initial rover direction:\n" +
                "Insert command (f = forward, b = backward, l = turn left, r = turn right):\n" +
                "Rover is at x:1 y:2 facing:n\n" +
                "Insert command (f = forward, b = backward, l = turn left, r = turn right):\n" +
                "forward movement is not allowed due to obstacle at position: (1,3)\n" +
                "Rover is at x:1 y:2 facing:n\n" +
                "Insert command (f = forward, b = backward, l = turn left, r = turn right):\n" +
                "Rover is at x:1 y:1 facing:n\n" +
                "Insert command (f = forward, b = backward, l = turn left, r = turn right):\n" +
                "Rover is at x:1 y:2 facing:n\n" +
                "Insert command (f = forward, b = backward, l = turn left, r = turn right):\n" +
                "Rover is at x:1 y:2 facing:e\n" +
                "Insert command (f = forward, b = backward, l = turn left, r = turn right):\n" +
                "Rover is at x:2 y:2 facing:e\n" +
                "Insert command (f = forward, b = backward, l = turn left, r = turn right):\n" +
                "Rover is at x:3 y:2 facing:e\n" +
                "Insert command (f = forward, b = backward, l = turn left, r = turn right):\n" +
                "Rover is at x:3 y:2 facing:n\n" +
                "Insert command (f = forward, b = backward, l = turn left, r = turn right):\n" +
                "Rover is at x:3 y:3 facing:n\n" +
                "Insert command (f = forward, b = backward, l = turn left, r = turn right):\n" +
                "Rover is at x:3 y:2 facing:n\n" +
                "Insert command (f = forward, b = backward, l = turn left, r = turn right):\n" +
                "Rover is at x:3 y:2 facing:e\n" +
                "Insert command (f = forward, b = backward, l = turn left, r = turn right):\n" +
                "Rover is at x:0 y:2 facing:e\n" +
                "Insert command (f = forward, b = backward, l = turn left, r = turn right):\n" +
                "Rover is at x:1 y:2 facing:e\n" +
                "Insert command (f = forward, b = backward, l = turn left, r = turn right):\n" +
                "Rover is at x:2 y:2 facing:e\n" +
                "Insert command (f = forward, b = backward, l = turn left, r = turn right):\n" +
                "Rover is at x:3 y:2 facing:e\n" +
                "Insert command (f = forward, b = backward, l = turn left, r = turn right):",
            outputStreamCaptor.toString().trim()
        )
    }

}