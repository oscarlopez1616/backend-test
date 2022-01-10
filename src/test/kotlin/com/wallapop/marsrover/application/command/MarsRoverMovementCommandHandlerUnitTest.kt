package com.wallapop.marsrover.application.command

import com.wallapop.marsrover.application.dto.CoordinateDTO
import com.wallapop.marsrover.domain.CoordinateVO
import com.wallapop.marsrover.domain.MarsAggregate
import com.wallapop.marsrover.domain.MarsRoverAggregate
import com.wallapop.marsrover.domain.OrientationVO
import com.wallapop.marsrover.domain.repository.MarsRepository
import com.wallapop.marsrover.domain.repository.MarsRoverRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows

@RunWith(MockitoJUnitRunner::class)
class MarsRoverMovementCommandHandlerUnitTest {

    @Mock
    private lateinit var marsRepositoryMock: MarsRepository
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
    fun `should execute forward movement when forward command has been provided`(){
        val givenCommand = MarsRoverMovementCommand("f")

        val marsRoverMovementCommandHandler = MarsRoverMovementCommandHandler(
            marsRepositoryMock,
            marsRoverRepositoryMock
        )

        val mars = MarsAggregate(
            3,
            3,
            listOf(CoordinateDTO(1,3)).map { coordinateDTO -> CoordinateVO(coordinateDTO.x,coordinateDTO.y) }
        )

        Mockito.`when`(
            marsRepositoryMock.loadOrFail()
        ).thenReturn(mars)

        val marsRoverAggregate = MarsRoverAggregate(CoordinateVO(1,1), OrientationVO("n"))
        Mockito.`when`(
            marsRoverRepositoryMock.loadOrFail()
        ).thenReturn(marsRoverAggregate)

        marsRoverMovementCommandHandler.dispatch(givenCommand)

        Mockito.verify(marsRepositoryMock,Mockito.times(1)).loadOrFail()
        Mockito.verify(marsRoverRepositoryMock,Mockito.times(1)).loadOrFail()

        Assertions.assertEquals(CoordinateVO(1,2),marsRoverAggregate.coordinate())
    }

    @Test
    fun `should avoid obstacles when forward movement over obstacle has been provided`() {
        val givenCommand = MarsRoverMovementCommand("f")

        val marsRoverMovementCommandHandler = MarsRoverMovementCommandHandler(
            marsRepositoryMock,
            marsRoverRepositoryMock
        )

        val mars = MarsAggregate(
            3,
            3,
            listOf(CoordinateDTO(1,3)).map { coordinateDTO -> CoordinateVO(coordinateDTO.x,coordinateDTO.y) }
        )

        Mockito.`when`(
            marsRepositoryMock.loadOrFail()
        ).thenReturn(mars)

        val marsRoverAggregate = MarsRoverAggregate(CoordinateVO(1,2), OrientationVO("n"))
        Mockito.`when`(
            marsRoverRepositoryMock.loadOrFail()
        ).thenReturn(marsRoverAggregate)

        marsRoverMovementCommandHandler.dispatch(givenCommand)

        Mockito.verify(marsRepositoryMock,Mockito.times(1)).loadOrFail()
        Mockito.verify(marsRoverRepositoryMock,Mockito.times(1)).loadOrFail()

        Assertions.assertEquals(CoordinateVO(1,2),marsRoverAggregate.coordinate())
    }

    @Test
    fun `should move forward into mars as sphere when forward movement at the end of the map has been provided`() {
        val givenCommand = MarsRoverMovementCommand("f")

        val marsRoverMovementCommandHandler = MarsRoverMovementCommandHandler(
            marsRepositoryMock,
            marsRoverRepositoryMock
        )

        val mars = MarsAggregate(
            3,
            3,
            listOf(CoordinateDTO(1,3)).map { coordinateDTO -> CoordinateVO(coordinateDTO.x,coordinateDTO.y) }
        )

        Mockito.`when`(
            marsRepositoryMock.loadOrFail()
        ).thenReturn(mars)

        val marsRoverAggregate = MarsRoverAggregate(CoordinateVO(1,3), OrientationVO("n"))
        Mockito.`when`(
            marsRoverRepositoryMock.loadOrFail()
        ).thenReturn(marsRoverAggregate)

        marsRoverMovementCommandHandler.dispatch(givenCommand)

        Mockito.verify(marsRepositoryMock,Mockito.times(1)).loadOrFail()
        Mockito.verify(marsRoverRepositoryMock,Mockito.times(1)).loadOrFail()

        Assertions.assertEquals(CoordinateVO(1,0),marsRoverAggregate.coordinate())
    }

    @Test
    fun `should thrown MarsRoverInvalidMovementException when  invalid movement has been provided`() {
        val givenCommand = MarsRoverMovementCommand("t")

        val marsRoverMovementCommandHandler = MarsRoverMovementCommandHandler(
            marsRepositoryMock,
            marsRoverRepositoryMock
        )

        assertThrows<MarsRoverMovementCommandHandler.MarsRoverInvalidMovementException> {
            marsRoverMovementCommandHandler.dispatch(givenCommand)
        }
    }

    @Test
    fun `should execute backward movement when backward command has been provided`(){
        val givenCommand = MarsRoverMovementCommand("b")

        val marsRoverMovementCommandHandler = MarsRoverMovementCommandHandler(
            marsRepositoryMock,
            marsRoverRepositoryMock
        )

        val mars = MarsAggregate(
            3,
            3,
            listOf(CoordinateDTO(1,3)).map { coordinateDTO -> CoordinateVO(coordinateDTO.x,coordinateDTO.y) }
        )

        Mockito.`when`(
            marsRepositoryMock.loadOrFail()
        ).thenReturn(mars)

        val marsRoverAggregate = MarsRoverAggregate(CoordinateVO(1,1), OrientationVO("n"))
        Mockito.`when`(
            marsRoverRepositoryMock.loadOrFail()
        ).thenReturn(marsRoverAggregate)

        marsRoverMovementCommandHandler.dispatch(givenCommand)

        Mockito.verify(marsRepositoryMock,Mockito.times(1)).loadOrFail()
        Mockito.verify(marsRoverRepositoryMock,Mockito.times(1)).loadOrFail()

        Assertions.assertEquals(CoordinateVO(1,0),marsRoverAggregate.coordinate())
    }

    @Test
    fun `should avoid obstacles when backward movement over obstacle has been provided`() {
        val givenCommand = MarsRoverMovementCommand("b")

        val marsRoverMovementCommandHandler = MarsRoverMovementCommandHandler(
            marsRepositoryMock,
            marsRoverRepositoryMock
        )

        val mars = MarsAggregate(
            3,
            3,
            listOf(CoordinateDTO(1,1)).map { coordinateDTO -> CoordinateVO(coordinateDTO.x,coordinateDTO.y) }
        )

        Mockito.`when`(
            marsRepositoryMock.loadOrFail()
        ).thenReturn(mars)

        val marsRoverAggregate = MarsRoverAggregate(CoordinateVO(1,2), OrientationVO("n"))
        Mockito.`when`(
            marsRoverRepositoryMock.loadOrFail()
        ).thenReturn(marsRoverAggregate)

        marsRoverMovementCommandHandler.dispatch(givenCommand)

        Mockito.verify(marsRepositoryMock,Mockito.times(1)).loadOrFail()
        Mockito.verify(marsRoverRepositoryMock,Mockito.times(1)).loadOrFail()

        Assertions.assertEquals(CoordinateVO(1,2),marsRoverAggregate.coordinate())
    }

    @Test
    fun `should move backward into mars as sphere when forward movement at the end of the map has been provided`() {
        val givenCommand = MarsRoverMovementCommand("b")

        val marsRoverMovementCommandHandler = MarsRoverMovementCommandHandler(
            marsRepositoryMock,
            marsRoverRepositoryMock
        )

        val mars = MarsAggregate(
            3,
            3,
            listOf(CoordinateDTO(0,0)).map { coordinateDTO -> CoordinateVO(coordinateDTO.x,coordinateDTO.y) }
        )

        Mockito.`when`(
            marsRepositoryMock.loadOrFail()
        ).thenReturn(mars)

        val marsRoverAggregate = MarsRoverAggregate(CoordinateVO(0,0), OrientationVO("n"))
        Mockito.`when`(
            marsRoverRepositoryMock.loadOrFail()
        ).thenReturn(marsRoverAggregate)

        marsRoverMovementCommandHandler.dispatch(givenCommand)

        Mockito.verify(marsRepositoryMock,Mockito.times(1)).loadOrFail()
        Mockito.verify(marsRoverRepositoryMock,Mockito.times(1)).loadOrFail()

        Assertions.assertEquals(CoordinateVO(0,3),marsRoverAggregate.coordinate())
    }

    @Test
    fun `should execute right movement properly when right command has been provided`(){
        val givenCommand = MarsRoverMovementCommand("r")

        val marsRoverMovementCommandHandler = MarsRoverMovementCommandHandler(
            marsRepositoryMock,
            marsRoverRepositoryMock
        )

        val mars = MarsAggregate(
            3,
            3,
            listOf(CoordinateDTO(1,3)).map { coordinateDTO -> CoordinateVO(coordinateDTO.x,coordinateDTO.y) }
        )

        Mockito.`when`(
            marsRepositoryMock.loadOrFail()
        ).thenReturn(mars)

        val marsRoverAggregate = MarsRoverAggregate(CoordinateVO(1,1), OrientationVO("n"))
        Mockito.`when`(
            marsRoverRepositoryMock.loadOrFail()
        ).thenReturn(marsRoverAggregate)

        marsRoverMovementCommandHandler.dispatch(givenCommand)

        Mockito.verify(marsRepositoryMock,Mockito.times(1)).loadOrFail()
        Mockito.verify(marsRoverRepositoryMock,Mockito.times(1)).loadOrFail()

        Assertions.assertEquals(OrientationVO("e"),marsRoverAggregate.orientation())

        marsRoverMovementCommandHandler.dispatch(MarsRoverMovementCommand("f"))
        Assertions.assertEquals(CoordinateVO(2,1),marsRoverAggregate.coordinate())
    }

    @Test
    fun `should execute left movement properly when right command has been provided`(){
        val givenCommand = MarsRoverMovementCommand("l")

        val marsRoverMovementCommandHandler = MarsRoverMovementCommandHandler(
            marsRepositoryMock,
            marsRoverRepositoryMock
        )

        val mars = MarsAggregate(
            3,
            3,
            listOf(CoordinateDTO(1,3)).map { coordinateDTO -> CoordinateVO(coordinateDTO.x,coordinateDTO.y) }
        )

        Mockito.`when`(
            marsRepositoryMock.loadOrFail()
        ).thenReturn(mars)

        val marsRoverAggregate = MarsRoverAggregate(CoordinateVO(1,1), OrientationVO("n"))
        Mockito.`when`(
            marsRoverRepositoryMock.loadOrFail()
        ).thenReturn(marsRoverAggregate)

        marsRoverMovementCommandHandler.dispatch(givenCommand)

        Mockito.verify(marsRepositoryMock,Mockito.times(1)).loadOrFail()
        Mockito.verify(marsRoverRepositoryMock,Mockito.times(1)).loadOrFail()

        Assertions.assertEquals(OrientationVO("w"),marsRoverAggregate.orientation())

        marsRoverMovementCommandHandler.dispatch(MarsRoverMovementCommand("f"))
        Assertions.assertEquals(CoordinateVO(0,1),marsRoverAggregate.coordinate())
    }
}