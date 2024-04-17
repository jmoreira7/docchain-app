package com.ufabc.docchain.data

import android.net.Uri
import com.google.gson.annotations.SerializedName

data class Exam(
    val patientName: String,
    val patientId: String,
    val doctorId: String,
    val description: String,
    val pdfUri: Uri?
)
