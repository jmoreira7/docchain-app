package com.ufabc.docchain.data

import com.google.gson.annotations.SerializedName

data class RawExam(
    @SerializedName("document") val patientName: String,
    @SerializedName("doctor_crm") val patientId: String,
    @SerializedName("exam_name") val doctorId: String,
    @SerializedName("description") val description: String,
    @SerializedName("attachment") val pdfUriBase64: String
)
