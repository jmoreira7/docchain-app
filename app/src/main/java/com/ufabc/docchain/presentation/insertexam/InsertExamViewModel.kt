package com.ufabc.docchain.presentation.insertexam

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ufabc.docchain.data.repository.BlockchainRepositoryI
import com.ufabc.docchain.data.repository.BlockchainRepositoryImpl
import com.ufabc.docchain.presentation.shared.ActivityStatus.*
import com.ufabc.docchain.presentation.insertexam.InsertExamViewModelAction.*
import com.ufabc.docchain.presentation.shared.ActivityStatus
import kotlinx.coroutines.delay
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

        val currentState = _state.value ?: InsertExamViewModelState()

        if (pdfFileSelected != null){
            val newState = currentState.copy(pdfIconVisible = true)

            postState(newState)
        } else {
            val newState = currentState.copy(pdfIconVisible = false)

            postState(newState)
        }
    }

    override fun sendExamData(
        context: Context,
        patientId: String,
        doctorId: String,
        examName: String,
        description: String
    ) {
        val validationSuccess = validateInputs(patientId, doctorId, examName, description)

        if (validationSuccess) {
            updateLoginStatus(LOADING)
            viewModelScope.launch {
                val result = blockchainRepository.postExam(
                    context = context,
                    patientId = patientId,
                    doctorId = doctorId,
                    examName = examName,
                    description = description,
                    pdfUri = pdfFileSelected
                )

                if (result) {
                    postAction(ShowSuccessToast)
                } else {
                    postAction(ShowFailToast)
                }

                delay(300L)

                updateLoginStatus(NORMAL)
            }
        }
    }

    private fun validateInputs(
        patientId: String,
        doctorId: String,
        examName: String,
        description: String
    ): Boolean {
        return if (patientId.isEmpty() || doctorId.isEmpty() || examName.isEmpty() || description.isEmpty()) {
            postAction(ShowEmptyInputFieldToast)
            false
        } else {
            true
        }
    }

    private fun updateLoginStatus(status: ActivityStatus) {
        val currentState = _state.value ?: InsertExamViewModelState()
        val newState = currentState.copy(insertExamStatus = status)

        postState(newState)
    }

    private fun postState(newState: InsertExamViewModelState?) {
        if (newState != null) {
            _state.postValue(newState)
        }
    }

    private fun postAction(action: InsertExamViewModelAction) {
        _action.value = action
    }
}