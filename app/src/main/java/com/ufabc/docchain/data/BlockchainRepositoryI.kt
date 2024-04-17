package com.ufabc.docchain.data

import android.net.Uri
import android.content.Context

interface BlockchainRepositoryI {
    suspend fun postExam(
        context: Context,
        patientName: String,
        patientId: String,
        doctorId: String,
        description: String,
        pdfUri: Uri?
    ): Boolean
}