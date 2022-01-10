package com.wallapop.marsrover.domain

import kotlin.jvm.Throws

data class MarsAggregate(
    val sizeX : Int,
    val sizeY : Int,
    private val obstacles : List<CoordinateVO>
) {
    init {
        naturalNumberGuard(sizeX)
        naturalNumberGuard(sizeY)
    }

    @Throws(OrientationVO.InvalidOrientationException::class)
    private fun naturalNumberGuard(natural: Int){
        if(natural < 0 ) throw InvalidMarsException("Is not allowed to use negative numbers in MarsAggregate for this placeholder")
    }

    fun isCoordinateAnObstacle(coordinate: CoordinateVO): Boolean{
        return obstacles.contains(coordinate)
    }

    class InvalidMarsException(message: String)  : RuntimeException(message)
}