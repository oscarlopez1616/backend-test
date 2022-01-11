package com.wallapop.marsrover.entrypoint.cli

import com.wallapop.marsrover.application.command.CreateMarsCommandHandler
import com.wallapop.marsrover.application.command.MarsRoverMovementCommandHandler
import com.wallapop.marsrover.application.command.SettleMarsRoverCommandHandler
import com.wallapop.marsrover.application.query.RetrieveMarsRoverInfoQueryHandler
import com.wallapop.marsrover.infraestructure.cli.controller.MarsRoverController
import com.wallapop.marsrover.infraestructure.persistence.memory.InMemoryMarsRepository
import com.wallapop.marsrover.infraestructure.persistence.memory.InMemoryMarsRoverRepository
import java.util.*

class App{
    fun initialize(){
        val marsRepository = InMemoryMarsRepository()
        val marsRoverRepository = InMemoryMarsRoverRepository()

        MarsRoverController(
            Scanner(System.`in`),
            CreateMarsCommandHandler(marsRepository),
            SettleMarsRoverCommandHandler(marsRoverRepository),
            MarsRoverMovementCommandHandler(marsRepository,marsRoverRepository),
            RetrieveMarsRoverInfoQueryHandler(marsRoverRepository)
        ).execute()
    }

    companion object {
        @JvmStatic
        fun main(args : Array<String>) {
            App().initialize()
        }
    }

}


