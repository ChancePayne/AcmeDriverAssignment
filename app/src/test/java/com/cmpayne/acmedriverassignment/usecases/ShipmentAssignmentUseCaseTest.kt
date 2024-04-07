package com.cmpayne.acmedriverassignment.usecases

import com.cmpayne.acmedriverassignment.domain.models.Address
import com.cmpayne.acmedriverassignment.domain.usecases.ShipmentAssignmentUseCase
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import com.cmpayne.acmedriverassignment.domain.usecases.ShipmentAssignmentUseCase.SuitabilityScoreForShipment as ScoreObject

class ShipmentAssignmentUseCaseTest {
    private val TEST_ADDRESSES = listOf(
        Address("2957", "Diamond Street", "#330", "San Francisco", "CA", "28465"),
        Address("18", "Sequoia Drive", "", "Berlin", "TX", "12345"),
        Address("1210", "Bedford Avenue", "", "Cherry Hill", "WA", "39572"),
        Address("3", "Chesterfield Road", "", "Sewell", "GA", "40284"),
        Address("24", "N Colgate Avenue", "", "Longport", "NJ", "22552"),
        Address("1804", "Sycamore Street", "", "Haddon Heights", "MA", "34552"),
        Address("430", "Paddock Court", "", "Landington", "FL", "19475"),
    )

    private val TEST_DRIVERS = listOf(
        "Sandra Branski",
        "Cynthia Chamberton",
        "Charlie Smithson",
    )

    private val TEST_DRIVER_SUITABILITY = listOf(
        mutableListOf(
            ScoreObject(9f, 0),
            ScoreObject(9f, 1),
            ScoreObject(9f, 2),
            ScoreObject(9f, 3),
            ScoreObject(9f, 4),
            ScoreObject(9f, 5),
            ScoreObject(9f, 6)
        ),
        mutableListOf(
            ScoreObject(11.25f, 0),
            ScoreObject(12.0f, 1),
            ScoreObject(11.25f, 2),
            ScoreObject(12.0f, 3),
            ScoreObject(11.25f, 4),
            ScoreObject(18.0f, 5),
            ScoreObject(12.0f, 6)
        ),
        mutableListOf(
            ScoreObject(11.25f, 0),
            ScoreObject(10.0f, 1),
            ScoreObject(11.25f, 2),
            ScoreObject(10.0f, 3),
            ScoreObject(11.25f, 4),
            ScoreObject(10.0f, 5),
            ScoreObject(10.0f, 6)
        )
    )

    private val useCase = ShipmentAssignmentUseCase()

    @Test
    fun calculateSuitabilitiesForDrivers() {
        for (i in TEST_DRIVERS.indices) {
            val driverSuitabilities = useCase.calculateSuitabilitiesForDriver(
                TEST_DRIVERS[i],
                TEST_ADDRESSES
            )

            assertEquals(
                TEST_DRIVER_SUITABILITY[i],
                driverSuitabilities,
                "Suitability Failed for driver $i: ${TEST_DRIVERS[i]}"
            )
        }
    }

    @Test
    fun optimizeDriverAssignments_TestData() {
        val dataForOptimization = listOf(
            mutableListOf(
                ScoreObject(9.0f, 0),
                ScoreObject(9.0f, 1),
                ScoreObject(9.0f, 2),
                ScoreObject(9.0f, 3),
                ScoreObject(9.0f, 4),
                ScoreObject(9.0f, 5),
                ScoreObject(9.0f, 6)
            ),
            mutableListOf(
                ScoreObject(11.25f, 0),
                ScoreObject(12.0f, 1),
                ScoreObject(11.25f, 2),
                ScoreObject(12.0f, 3),
                ScoreObject(11.25f, 4),
                ScoreObject(18.0f, 5),
                ScoreObject(12.0f, 6)
            ),
            mutableListOf(
                ScoreObject(7.5f, 0),
                ScoreObject(10.5f, 1),
                ScoreObject(7.5f, 2),
                ScoreObject(7.0f, 3),
                ScoreObject(7.5f, 4),
                ScoreObject(7.0f, 5),
                ScoreObject(10.5f, 6)
            ),
            mutableListOf(
                ScoreObject(11.25f, 0),
                ScoreObject(10.0f, 1),
                ScoreObject(11.25f, 2),
                ScoreObject(10.0f, 3),
                ScoreObject(11.25f, 4),
                ScoreObject(10.0f, 5),
                ScoreObject(10.0f, 6)
            ),
            mutableListOf(
                ScoreObject(9.0f, 0),
                ScoreObject(11.0f, 1),
                ScoreObject(9.0f, 2),
                ScoreObject(11.0f, 3),
                ScoreObject(9.0f, 4),
                ScoreObject(11.0f, 5),
                ScoreObject(11.0f, 6)
            ),
            mutableListOf(
                ScoreObject(6.0f, 0),
                ScoreObject(12.0f, 1),
                ScoreObject(6.0f, 2),
                ScoreObject(8.0f, 3),
                ScoreObject(6.0f, 4),
                ScoreObject(8.0f, 5),
                ScoreObject(12.0f, 6)
            ),
            mutableListOf(
                ScoreObject(9.0f, 0),
                ScoreObject(8.0f, 1),
                ScoreObject(9.0f, 2),
                ScoreObject(8.0f, 3),
                ScoreObject(9.0f, 4),
                ScoreObject(12.0f, 5),
                ScoreObject(8.0f, 6)
            )
        )

        val result = useCase.optimizeDriverAssignments(dataForOptimization)

        assertEquals(
            listOf(0, 5, 1, 2, 3, 6, 4),
            result
        )
    }

    @Test
    fun optimizeDriverAssignments_Simple() {
        val dataForOptimization = listOf(
            mutableListOf(ScoreObject(3f, 0), ScoreObject(0f, 1), ScoreObject(4f, 2)),
            mutableListOf(ScoreObject(5f, 0), ScoreObject(0f, 1), ScoreObject(2f, 2)),
            mutableListOf(ScoreObject(0f, 0), ScoreObject(1f, 1), ScoreObject(7f, 2))
        )

        val result = useCase.optimizeDriverAssignments(dataForOptimization)

        assertEquals(
            listOf(1, 0, 2),
            result
        )
    }
}