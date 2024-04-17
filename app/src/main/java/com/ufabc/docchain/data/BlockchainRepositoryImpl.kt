package com.ufabc.docchain.data

import android.content.Context
import android.net.Uri
import android.util.Base64
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

class BlockchainRepositoryImpl : BlockchainRepositoryI {

    private val apiMechanism = FastApiMechanism()

    override suspend fun postExam(
        context: Context,
        patientName: String,
        patientId: String,
        doctorId: String,
        description: String,
        pdfUri: Uri?
    ): Boolean {
        val pdfBase64 = pdfUri?.let { convertPdfUriToBase64(context, it) } ?: EMPTY_STRING

        return withContext(Dispatchers.IO) {
            try {
                val response = apiMechanism.api.postExam(
                    RawExam(
                        patientName,
                        patientId,
                        doctorId,
                        description,
                        pdfBase64
                    )
                )
                if (response.isSuccessful) {
                    true
                } else {
                    Log.e(LOG_TAG, "Response not successful.")
                    false
                }
            } catch (e: IOException) {
                Log.e(LOG_TAG, "Failed with IOException.", e)
                false
            } catch (e: HttpException) {
                Log.e(LOG_TAG, "HttpException, unexpected response.", e)
                false
            }
        }
    }


    private fun convertPdfUriToBase64(context: Context, uri: Uri): String? {
        var inputStream: InputStream? = null

        try {
            inputStream = context.contentResolver.openInputStream(uri)

            val byteArrayOutputStream = ByteArrayOutputStream()
            val buffer = ByteArray(1024)
            var bytesRead: Int

            while (inputStream?.read(buffer).also { bytesRead = it!! } != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead)
            }

            val bytes = byteArrayOutputStream.toByteArray()

            return Base64.encodeToString(bytes, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            inputStream?.close()
        }
        return null
    }

    companion object {
        private const val LOG_TAG = "BlockchainRepositoryImpl"
        private const val EMPTY_STRING = ""
    }
}