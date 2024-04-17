package com.ufabc.docchain.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FastApiMechanism {

    val api: FastApi by lazy {
        Retrofit.Builder()
            .baseUrl("http://127.0.0.1:8000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FastApi::class.java)
    }
}