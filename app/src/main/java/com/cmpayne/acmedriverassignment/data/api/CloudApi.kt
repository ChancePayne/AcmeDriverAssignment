package com.cmpayne.acmedriverassignment.data.api

import retrofit2.Response
import retrofit2.http.GET

interface CloudApi {

    @GET("CMs74xQrp99zw8y/download/drivers.json")
    suspend fun getDrivers(): Response<List<String>>

    @GET("3wrz2fm6EexQbQE/download/shipments.json")
    suspend fun getShipments(): Response<List<String>>
}