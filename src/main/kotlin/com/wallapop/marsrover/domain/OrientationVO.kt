package com.wallapop.marsrover.domain

import kotlin.jvm.Throws


data class OrientationVO(var value: String){
    init {
        validOrientationConstraint(value)
    }

    @Throws(InvalidOrientationException::class)
    private fun validOrientationConstraint(orientation: String){
        if ( !"nwse".contains(orientation) ) throw InvalidOrientationException("Invalid OrientationVO $orientation")
    }

    override fun toString(): String = value

    class InvalidOrientationException(message: String)  : RuntimeException(message)
}