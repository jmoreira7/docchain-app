package com.ufabc.docchain.data.models

import com.google.gson.annotations.SerializedName

data class RawExam(
    @SerializedName("document") val patientId: String,
    @SerializedName("doctor_crm") val doctorId: String,
    @SerializedName("exam_name") val examName: String,
    @SerializedName("description") val description: String,
    @SerializedName("attachment") val pdfBase64: String
)
