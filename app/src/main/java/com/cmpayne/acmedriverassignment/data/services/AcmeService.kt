package com.cmpayne.acmedriverassignment.data.services

import com.cmpayne.acmedriverassignment.data.DomainError
import com.cmpayne.acmedriverassignment.data.Result
import com.cmpayne.acmedriverassignment.data.api.CloudNetworkClient
import com.cmpayne.acmedriverassignment.data.dtos.AddressDto
import com.cmpayne.acmedriverassignment.domain.models.Address

/* I probably went too far with this... lol. I figured why simulate an api when I can access a
real on through Github. So this class does that. It pulls json files (stored in this repo)
just like you would with a real API, the downside is that Github returns the files using a
Base64 encoding system which makes the calls a bit more convuluted. */
class AcmeService private constructor() {

    /* For a larger app, I would implement a better caching system, perhaps using Room, but for this one
    I have already gone overboard on the scope so this is a simple way of reducing the number of calls
    to the API endpoint. */
    companion object {
        @Volatile
        private var instance: AcmeService? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: AcmeService().also { instance = it }
            }
    }

    private lateinit var driverCache: List<String>
    private lateinit var shipmentCache: List<Address>

    suspend fun fetchDrivers(): Result<List<String>, DomainError> {
        return if (this::driverCache.isInitialized) {
            Result.Success(driverCache)
        } else {
            val response = CloudNetworkClient.cloudApi.getDrivers()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Result.Success(body)
            } else {
                response.errorBody()
                Result.Error(DomainError.GeneralError)
            }
        }
    }

    suspend fun fetchShipments(): Result<List<Address>, DomainError> {
        return if (this::shipmentCache.isInitialized) {
            Result.Success(shipmentCache)
        } else {
            val response = CloudNetworkClient.cloudApi.getShipments()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Result.Success(body.map { AddressDto(it).toDomainModel() })
            } else {
                Result.Error(DomainError.GeneralError)
            }
        }
    }
}
