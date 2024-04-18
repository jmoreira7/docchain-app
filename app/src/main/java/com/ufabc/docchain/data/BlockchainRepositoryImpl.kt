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
        patientId: String,
        doctorId: String,
        examName: String,
        description: String,
        pdfUri: Uri?
    ): Boolean {
        val pdfBase64 = pdfUri?.let { convertPdfUriToBase64(context, it) } ?: EMPTY_STRING

        Log.d(LOG_TAG, "Selected PDF Uri: [$pdfUri]")

        return withContext(Dispatchers.IO) {
            try {
                val response = apiMechanism.api.postExam(
                    RawExam(
                        patientId = patientId,
                        doctorId = doctorId,
                        examName = examName,
                        description = description,
                        pdfBase64 = pdfBase64
                    )
                )

                Log.d(LOG_TAG, "postExam response code: [${response.code()}]")
                Log.d(LOG_TAG, "postExam error body: [${response.errorBody()?.string()}]")
                Log.d(LOG_TAG, "postExam body data: [${response.body()}]")

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

    override suspend fun getExams(context: Context, userId: String): List<Exam> {
        return withContext(Dispatchers.IO) {
            var examsList: List<Exam> = listOf()

            try {
                Log.d("DEBUG", "getExams userId: [$userId]")
                val response = apiMechanism.api.getExams(userId)

                Log.d(LOG_TAG, "getExam response code: [${response.code()}]")
                Log.d(LOG_TAG, "getExam error body: [${response.errorBody()?.string()}]")

                if (response.isSuccessful) {
                    examsList = response.body()?.data?.blocks?.map { item ->
                        if (item.examData.pdfBase64.isNotEmpty()) {
                            ExamMapper.fromRawExam(context, item.examData)
                        } else {
                            Exam(
                                patientId = item.examData.patientId,
                                doctorId = item.examData.doctorId,
                                examName = item.examData.examName,
                                description = item.examData.description,
                                pdfUri = null
                            )
                        }
                    } ?: listOf()
                }

                Log.d(LOG_TAG, "Response code: [${response.code()}]")
                Log.d(LOG_TAG, "Response error body: [${response.errorBody()?.string()}]")

                return@withContext examsList
            } catch (e: IOException) {
                Log.e(LOG_TAG, "Failed with IOException.", e)
            } catch (e: HttpException) {
                Log.e(LOG_TAG, "HttpException, unexpected response.", e)
            }
            listOf()
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