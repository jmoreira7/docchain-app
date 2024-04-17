package com.ufabc.docchain.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface FastApi {
    @POST("/exams/register-exam")
    suspend fun postExam(@Body exam: RawExam): Response<RawExam>
}