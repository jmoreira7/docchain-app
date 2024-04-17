package com.ufabc.docchain.data

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.UUID

object ExamMapper {
    fun fromRawExam(context: Context, rawExam: RawExam): Exam {
        val bytes = base64ToBytes(rawExam.pdfBase64)
        val uuid = UUID.randomUUID().toString()
        val fileName = "${rawExam.examName}-$uuid.pdf"
        val file = savePdfToExternalStorage(context, bytes, fileName)
        val uri = getFileUri(context, file)

        return Exam(
            patientName = rawExam.examName,
            patientId = rawExam.examName,
            doctorId = rawExam.examName,
            description = rawExam.description,
            pdfUri = uri
        )
    }

    private fun base64ToBytes(base64String: String): ByteArray? {
        return if (base64String.isEmpty()) {
            null
        } else {
            android.util.Base64.decode(base64String, android.util.Base64.DEFAULT)
        }
    }

    private fun savePdfToExternalStorage(
        context: Context,
        bytes: ByteArray?,
        fileName: String
    ): File? {
        val file = File(context.getExternalFilesDir(null), fileName)

        try {
            FileOutputStream(file).apply {
                write(bytes)
                close()
            }
            return file
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    private fun getFileUri(context: Context, file: File?): Uri? {
        return file?.let {
            FileProvider.getUriForFile(
                context,
                "com.ufabc.docchain.provider",
                it
            )
        }
    }
}