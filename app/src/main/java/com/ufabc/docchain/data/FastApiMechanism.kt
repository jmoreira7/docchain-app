package com.ufabc.docchain.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class FastApiMechanism {

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.HOURS)
        .readTimeout(1, TimeUnit.HOURS)
        .connectTimeout(1, TimeUnit.HOURS)
        .build()

    val api: FastApi by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000")
//            .baseUrl("https://docchain.onrender.com/docs")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(FastApi::class.java)
    }
}