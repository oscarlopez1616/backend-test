package com.wallapop.marsrover.infraestructure.cli.controller

import com.wallapop.marsrover.application.command.*
import com.wallapop.marsrover.application.dto.CoordinateDTO
import com.wallapop.marsrover.application.dto.MarsRoverDTO
import com.wallapop.marsrover.application.query.RetrieveMarsRoverInfoQueryHandler
import java.util.*

class MarsRoverController(
    private val reader: Scanner,
    private val createMarsCommandHandler: CreateMarsCommandHandler,
    private val settleMarsRoverCommandHandler: SettleMarsRoverCommandHandler,
    private val marsRoverMovementCommandHandler: MarsRoverMovementCommandHandler,
    private val retrieveMarsRoverInfoQueryHandler: RetrieveMarsRoverInfoQueryHandler
){

    fun execute(){
        println("Insert horizontal map size:")
        val sizex = reader.nextInt()
        println("Insert vertical map size:")
        val sizey = reader.nextInt()

        println("Insert horizontal initial rover position:")
        val roverx = reader.nextInt()
        println("Insert vertical initial rover position:")
        val rovery = reader.nextInt()
        println("Insert initial rover direction:")
        val roverz = reader.next() //n = north, e = east, w = west, s = south

        createMarsCommandHandler.dispatch(
            CreateMarsCommand(
                sizex,
                sizey,
                listOf(CoordinateDTO(1,3)))
        )

        settleMarsRoverCommandHandler.dispatch(
            SettleMarsRoverCommand(MarsRoverDTO(CoordinateDTO(roverx,rovery),roverz))
        )

        do {
            println("Insert command (f = forward, b = backward, l = turn left, r = turn right):")
            val marRoverMovement = reader.next()
            marsRoverMovementCommandHandler.dispatch(
                MarsRoverMovementCommand(marRoverMovement)
            )

           val marsRoverDTO = retrieveMarsRoverInfoQueryHandler.dispatch()

            println(
                String.format(
                    "Rover is at x:%d y:%d facing:%s",
                    marsRoverDTO.coordinateDTO.x,
                    marsRoverDTO.coordinateDTO.y,
                    marsRoverDTO.z
                )
            )
       } while (true)
    }

}