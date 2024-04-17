package com.ufabc.docchain.presentation

import android.net.Uri

interface InsertExamEvent {
    fun pdfFileSelected(pdfUri: Uri?)
}