package com.cmpayne.acmedriverassignment.data.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CloudNetworkClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://cloud.chenzarchondie.xyz/s/")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    val cloudApi: CloudApi by lazy { retrofit.create(CloudApi::class.java) }
}