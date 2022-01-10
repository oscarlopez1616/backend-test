package com.wallapop.marsrover.application.query

import com.wallapop.marsrover.application.dto.MarsRoverDTO
import com.wallapop.marsrover.application.dto.toMarsRoverDTO
import com.wallapop.marsrover.domain.repository.MarsRoverRepository

class RetrieveMarsRoverInfoQueryHandler(
    private val marsRoverRepository: MarsRoverRepository
){
    fun dispatch(): MarsRoverDTO {
        return marsRoverRepository.loadOrFail().toMarsRoverDTO()
    }
}