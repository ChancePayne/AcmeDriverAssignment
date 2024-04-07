package com.cmpayne.acmedriverassignment.domain.usecases

import com.cmpayne.acmedriverassignment.data.DomainError
import com.cmpayne.acmedriverassignment.data.Result
import com.cmpayne.acmedriverassignment.data.services.AcmeService
import com.cmpayne.acmedriverassignment.domain.models.Address
import com.cmpayne.acmedriverassignment.framework.extensions.nonOneFactors
import com.cmpayne.acmedriverassignment.framework.extensions.numberOfConsonants
import com.cmpayne.acmedriverassignment.framework.extensions.numberOfVowels

class ShipmentAssignmentUseCase {

    data class SuitabilityScoreForShipment(var score: Float, val shipmentId: Int)

    private val acmeService = AcmeService.getInstance()

    suspend fun getAssignmentForDriver(driverName: String): Result<Address, DomainError> {
        val driversResult = acmeService.fetchDrivers()
        val shipmentsResult = acmeService.fetchShipments()
        return if (driversResult is Result.Success && shipmentsResult is Result.Success) {
            val address = calculateDriverAssignments(driversResult.data, shipmentsResult.data)[driverName]
            if (address != null) {
                Result.Success(address)
            } else {
                Result.Error(DomainError.GeneralError)
            }
        } else {
            Result.Error(
                when {
                    shipmentsResult is Result.Error -> shipmentsResult.error
                    driversResult is Result.Error -> driversResult.error
                    else -> DomainError.GeneralError
                }
            )
        }
    }

    /* It isn't a good idea to calculate driver assignments on a device like this. For a product like this,
    it is essential that each driver gets the same list of assignments. It is also a lot of wasted processing
    power to optimize this on every driver's account, especially when the list of shipments and drivers gets
    longer. One possible way to improve this would be to move these calculations to a server and run them as
    a web job every time a new list of shipments gets created. If a server isn't an option, I would run the
    calculations on the first device loaded once the data is ready and then store them in some cloud storage
    system like firebase. That way there is a single source of truth that the rest of the devices can reference.
    If neither of those are an option, I would at least store the processed assignments locally. It can be run
    as a daily job at a set time or on first launch and then stored until the next set of addresses is available. */
    private fun calculateDriverAssignments(drivers: List<String>, shipments: List<Address>): HashMap<String, Address> {
        val driverSuitabilities = drivers.map { calculateSuitabilitiesForDriver(it, shipments) }
        val assignments = optimizeDriverAssignments(driverSuitabilities)
        return processAssignmentsToData(assignments, drivers, shipments)
    }

    private fun processAssignmentsToData(
        assignments: List<Int>,
        drivers: List<String>,
        shipments: List<Address>
    ): HashMap<String, Address> {
        val driverTable = hashMapOf<String, Address>()
        assignments.forEachIndexed { driverIndex, shipmentIndex ->
            driverTable[drivers[driverIndex]] = shipments[shipmentIndex]
        }
        return driverTable
    }

    fun calculateSuitabilitiesForDriver(
        driver: String,
        shipments: List<Address>
    ): List<SuitabilityScoreForShipment> {
        val suitabilities = shipments.mapIndexed { index, shipment ->
            /* I'm assuming here and elsewhere that "length" includes spaces, this would be easily
            changed if that wasn't the case. Also, spaces aren't considered consonants or vowels. */
            SuitabilityScoreForShipment(
                score = if (shipment.streetName.length % 2 == 0) {
                    /* If the length of the shipment's destination street name is even, the base suitability
                                score (SS) is the number of vowels in the driver’s name multiplied by 1.5. */
                    driver.numberOfVowels() * 1.5f
                } else {
                    /* If the length of the shipment's destination street name is odd, the base SS is
                                the number of consonants in the driver’s name multiplied by 1. */
                    driver.numberOfConsonants() * 1f
                },
                shipmentId = index
            )
        }.toMutableList()
        val driverFactors = driver.length.nonOneFactors()
        shipments.forEachIndexed { index, address ->
            /* If the length of the shipment's destination street name shares any common factors
            (besides 1) with the length of the driver’s name, the SS is increased by 50% above the
            base SS. */
            if (driverFactors.intersect(address.streetName.length.nonOneFactors()).isNotEmpty()) {
                suitabilities[index].score *= 1.5f
                /*
                                suitabilities[index] = SuitabilityScoreForShipment(
                                    suitabilities[index].score * 1.5f,
                                    suitabilities[index].shipmentId)
                */
            }
        }
        return suitabilities
    }

    /* When one is selected from the list display the correct shipment destination to that driver in a
    way that maximizes the total SS over the set of drivers. Each driver can only have one shipment
    and each shipment can only be offered to one driver. */
    fun optimizeDriverAssignments(
        suitabilities: List<List<SuitabilityScoreForShipment>>
    ): List<Int> {
        lateinit var proposedSolution: MutableList<Int>
        var proposedSuitability = 0f
        var proposedStartIndex = 0
        val solutionUnderTest = mutableListOf<Int>()
        var suitabilityUnderTest = 0f

        for (startIndex in suitabilities.indices) {
            val testSuitability = mutableListOf<MutableList<SuitabilityScoreForShipment>>()
            suitabilities.forEach { testSuitability.add(it.toMutableList()) }

            repeat(startIndex) {
                testSuitability.add(testSuitability.removeFirst())
            }

            testSuitability.forEach { driverSuitabilityList ->
                val preferredRoute = driverSuitabilityList.maxByOrNull { it.score } ?: driverSuitabilityList.first()
                val indexOfPreferredRoute = driverSuitabilityList.indexOf(preferredRoute)
                testSuitability.forEach { it.removeAt(indexOfPreferredRoute) }
                solutionUnderTest.add(preferredRoute.shipmentId)
                suitabilityUnderTest += preferredRoute.score
            }

            if (suitabilityUnderTest > proposedSuitability) {
                proposedSolution = solutionUnderTest.toMutableList()
                proposedSuitability = suitabilityUnderTest
                proposedStartIndex = startIndex
            }

            solutionUnderTest.clear()
            suitabilityUnderTest = 0f
        }

        repeat(proposedStartIndex) {
            proposedSolution.add(0, proposedSolution.removeLast())
        }

        return proposedSolution
    }
}