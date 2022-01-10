package com.wallapop.marsrover.domain

data class CoordinateVO(var x:Int, var y:Int){
    override fun toString(): String {
        return "($x,$y)"
    }
}