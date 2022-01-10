package com.wallapop.marsrover.domain

import kotlin.jvm.Throws

data class MarsRoverAggregate(
    private var coordinate: CoordinateVO,
    private var orientation : OrientationVO
){
    fun coordinate() = coordinate

    fun orientation() = orientation

    fun moveForward(mars: MarsAggregate) {
        var newCoordinate = CoordinateVO(coordinate.x,coordinate.y)
        if (orientation.value == "n") {
            newCoordinate.y += 1
        }
        if (orientation.value == "w") {
            newCoordinate.x -= 1
        }
        if (orientation.value == "s") {
            newCoordinate.y -= 1
        }
        if (orientation.value == "e") {
            newCoordinate.x += 1
        }

        newCoordinate = commandOverflow(newCoordinate, mars)

        if(mars.isCoordinateAnObstacle(newCoordinate)){
            println("forward movement is not allowed due to obstacle at position: $newCoordinate")
        }else{
            coordinate = newCoordinate
        }
    }

    fun moveBackWard(mars: MarsAggregate) {
        var newCoordinate = CoordinateVO(coordinate.x,coordinate.y)
        if (orientation.value == "n") {
            newCoordinate.y -= 1
        }
        if (orientation.value == "w") {
            newCoordinate.x += 1
        }
        if (orientation.value == "s") {
            newCoordinate.y += 1
        }
        if (orientation.value == "e") {
            newCoordinate.x -= 1
        }

        newCoordinate =  commandOverflow(newCoordinate, mars)

        if(mars.isCoordinateAnObstacle(newCoordinate)){
            println("backward movement is not allowed due to obstacle at position: $newCoordinate")
        }else{
            coordinate = newCoordinate
        }

    }

    private fun commandOverflow(newCoordinate: CoordinateVO, mars: MarsAggregate): CoordinateVO {
        if (newCoordinate.x != coordinate.x) {
            newCoordinate.x = if (newCoordinate.x <= mars.sizeX && newCoordinate.x > 0) {
                newCoordinate.x
            } else {
                if(newCoordinate.x < 0){
                    mars.sizeX
                }else{
                    0
                }
            }
        }
        if (newCoordinate.y != coordinate.y) {
            newCoordinate.y = if (newCoordinate.y <= mars.sizeY && newCoordinate.y > 0) {
                newCoordinate.y
            } else {
                if(newCoordinate.y < 0){
                    mars.sizeY
                }else{
                    0
                }
            }
        }
        return newCoordinate
    }

    @Throws(NotAllowedMovementException::class)
    fun rotateLeft() {
        if (orientation.value == "n") {
            orientation = OrientationVO("w")
            return
        }
        if (orientation.value == "w") {
            orientation =  OrientationVO("s")
            return
        }
        if (orientation.value == "s") {
            orientation = OrientationVO("e")
            return
        }
        if (orientation.value == "e") {
            orientation = OrientationVO("n")
            return
        }

        throw NotAllowedMovementException("Invalid previous orientation value: ${orientation.value}")
    }

    @Throws(NotAllowedMovementException::class)
    fun rotateRight() {
        if (orientation.value == "n") {
            orientation = OrientationVO("e")
            return
        }
        if (orientation.value == "e") {
            orientation = OrientationVO("s")
            return
        }
        if (orientation.value == "s") {
            orientation = OrientationVO("w")
            return
        }
        if (orientation.value == "w") {
            orientation = OrientationVO("n")
            return
        }

        throw NotAllowedMovementException("Invalid previous orientation value: ${orientation.value}")
    }

    class NotAllowedMovementException(message: String)  : RuntimeException(message)

}

