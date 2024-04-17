package com.ufabc.docchain.presentation

import android.content.Context
import android.net.Uri

interface InsertExamEvent {
    fun pdfFileSelected(pdfUri: Uri?)

    fun sendExamData(
        context: Context,
        patientId: String,
        doctorId: String,
        examName: String,
        description: String
    )
}