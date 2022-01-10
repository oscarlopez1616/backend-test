package com.wallapop.marsrover.application.command

import com.wallapop.marsrover.application.dto.CoordinateDTO
import com.wallapop.marsrover.domain.CoordinateVO
import com.wallapop.marsrover.domain.MarsAggregate
import com.wallapop.marsrover.domain.repository.MarsRepository
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
class CreateMarsCommandHandlerUnitTest {

    @Mock
    private lateinit var marsRepositoryMock: MarsRepository

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
        val givenCreateMarsCommand = CreateMarsCommand(3,3, listOf(CoordinateDTO(1,3)))

        val createdMarsCommandHandler = CreateMarsCommandHandler(marsRepositoryMock)
        createdMarsCommandHandler.dispatch(givenCreateMarsCommand)

        val mars = MarsAggregate(
            givenCreateMarsCommand.sizeX,
            givenCreateMarsCommand.sizeY,
            givenCreateMarsCommand.obstacle.map { coordinateDTO -> CoordinateVO(coordinateDTO.x,coordinateDTO.y) }
        )

        Mockito.verify(marsRepositoryMock,Mockito.times(1)).save(mars)
    }

    @Test
    fun `should Thrown InvalidMarsException When Bad Size Has Been provided`() {
        val givenCreateMarsCommand = CreateMarsCommand(-3,-3, listOf(CoordinateDTO(1,3)))

        val createdMarsCommandHandler = CreateMarsCommandHandler(marsRepositoryMock)

        assertThrows<MarsAggregate.InvalidMarsException> {
            createdMarsCommandHandler.dispatch(givenCreateMarsCommand)
        }
    }
}