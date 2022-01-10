package com.wallapop.marsrover.application.dto

import com.wallapop.marsrover.domain.CoordinateVO

data class CoordinateDTO(
    val x:Int,
    val y:Int
)

fun CoordinateVO.toCoordinateDTO() = CoordinateDTO(
    x=x,
    y=y,
)