package com.wallapop.marsrover.application.query

import com.wallapop.marsrover.application.dto.toMarsRoverDTO
import com.wallapop.marsrover.domain.AggregateNotFoundException
import com.wallapop.marsrover.domain.CoordinateVO
import com.wallapop.marsrover.domain.MarsRoverAggregate
import com.wallapop.marsrover.domain.OrientationVO
import com.wallapop.marsrover.domain.repository.MarsRoverRepository
import org.junit.Test
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RetrieveMarsRoverInfoQueryHandlerUnitTest {

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
    fun `should Load MarsRover When proper initialization has been done`() {
        val expectedMarsRoverAggregate = MarsRoverAggregate(
            CoordinateVO(1,1),
            OrientationVO("n")
        )

        Mockito.`when`(marsRoverRepositoryMock.loadOrFail()).thenReturn(expectedMarsRoverAggregate)

        val retrieveMarsRoverInfoQueryHandler = RetrieveMarsRoverInfoQueryHandler(marsRoverRepositoryMock)
        val marsRoverDTO = retrieveMarsRoverInfoQueryHandler.dispatch()

        Mockito.verify(marsRoverRepositoryMock, Mockito.times(1)).loadOrFail()

        Assertions.assertEquals(expectedMarsRoverAggregate.toMarsRoverDTO(),marsRoverDTO)
    }

    @Test
    fun `should Thrown AggregateNotFoundException When not exist MarsRover`() {
        Mockito.`when`(marsRoverRepositoryMock.loadOrFail()).thenThrow(AggregateNotFoundException("Mars Rover does not exist"))

        val retrieveMarsRoverInfoQueryHandler = RetrieveMarsRoverInfoQueryHandler(marsRoverRepositoryMock)
        assertThrows<AggregateNotFoundException>{
            retrieveMarsRoverInfoQueryHandler.dispatch()
        }
    }
}