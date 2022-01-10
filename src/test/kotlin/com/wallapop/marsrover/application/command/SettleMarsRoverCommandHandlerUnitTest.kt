package com.wallapop.marsrover.application.command

import com.wallapop.marsrover.application.dto.CoordinateDTO
import com.wallapop.marsrover.application.dto.MarsRoverDTO
import com.wallapop.marsrover.domain.CoordinateVO
import com.wallapop.marsrover.domain.MarsRoverAggregate
import com.wallapop.marsrover.domain.OrientationVO
import com.wallapop.marsrover.domain.repository.MarsRoverRepository
import org.junit.Test
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SettleMarsRoverCommandHandlerUnitTest {

    @Mock
    private lateinit var marsRoverRepositoryMock: MarsRoverRepository

    private lateinit var closeable: AutoCloseable

    @BeforeEach
    fun beforeEach() {
        closeable = MockitoAnnotations.openMocks(this)
    }

    @AfterEach
    @Throws(Exception::class)
    fun afterEach() {
        closeable.close()
    }

    @Test
    fun `should Create Mars When Proper Size And Obstacle Has Been provided`() {
        val givenSettleMarsRoverCommand = SettleMarsRoverCommand(
            MarsRoverDTO(
                CoordinateDTO(1, 1),
                "n"
            )
        )

        val settleMarsRoverCommandHandler = SettleMarsRoverCommandHandler(marsRoverRepositoryMock)
        settleMarsRoverCommandHandler.dispatch(givenSettleMarsRoverCommand)

        val marsRoverAggregate = MarsRoverAggregate(
            CoordinateVO(
                givenSettleMarsRoverCommand.marsRoverDTO.coordinateDTO.x,
                givenSettleMarsRoverCommand.marsRoverDTO.coordinateDTO.y
            ),
            OrientationVO(givenSettleMarsRoverCommand.marsRoverDTO.z)
        )

        Mockito.verify(marsRoverRepositoryMock, Mockito.times(1)).save(marsRoverAggregate)
    }

    @Test
    fun `should Thrown InvalidOrientationException When Bad Orientation Has Been provided`() {
        val givenSettleMarsRoverCommand = SettleMarsRoverCommand(
            MarsRoverDTO(
                CoordinateDTO(1, 1),
                "p"
            )
        )

        val settleMarsRoverCommandHandler = SettleMarsRoverCommandHandler(marsRoverRepositoryMock)

        assertThrows<OrientationVO.InvalidOrientationException> {
            settleMarsRoverCommandHandler.dispatch(givenSettleMarsRoverCommand)
        }
    }
}