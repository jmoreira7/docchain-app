package com.ufabc.docchain.presentation

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ufabc.docchain.data.BlockchainRepositoryI
import com.ufabc.docchain.data.BlockchainRepositoryImpl
import com.ufabc.docchain.presentation.InsertExamViewModelAction.*
import kotlinx.coroutines.launch

class InsertExamViewModel : ViewModel(), InsertExamEvent {

    private val blockchainRepository: BlockchainRepositoryI = BlockchainRepositoryImpl()

    private val _state = MutableLiveData<InsertExamViewModelState>()

    private val _action = MutableLiveData<InsertExamViewModelAction>()

    private var pdfFileSelected: Uri? = null

    val state: LiveData<InsertExamViewModelState>
        get() = _state

    val action: LiveData<InsertExamViewModelAction>
        get() = _action

    init {
        _state.postValue(InsertExamViewModelState())
    }

    override fun pdfFileSelected(pdfUri: Uri?) {
        pdfFileSelected = pdfUri
    }

    override fun sendExamData(
        context: Context,
        patientId: String,
        doctorId: String,
        examName: String,
        description: String
    ) {
        viewModelScope.launch {
            val result = blockchainRepository.postExam(
                context,
                patientId,
                doctorId,
                examName,
                description,
                pdfFileSelected
            )
            Log.d("DEBUG", "InsertExamViewModel Result: [$result]")
            if (result) {
                postAction(ShowSuccessDialog)
            } else {
                postAction(ShowFailDialog)
            }
        }
    }

    private fun postAction(action: InsertExamViewModelAction) {
        _action.value = action
    }
}