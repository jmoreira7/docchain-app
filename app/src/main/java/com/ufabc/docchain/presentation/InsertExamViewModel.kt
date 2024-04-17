package com.ufabc.docchain.presentation

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InsertExamViewModel: ViewModel(), InsertExamEvent {

    private val _state = MutableLiveData<InsertExamViewModelState>()

    private val _action = MutableLiveData<InsertExamViewModelAction>()

    val state: LiveData<InsertExamViewModelState>
        get() = _state

    val action: LiveData<InsertExamViewModelAction>
        get() = _action

    init {
        _state.postValue(InsertExamViewModelState())
    }

    override fun pdfFileSelected(pdfUri: Uri?) {
        // TODO
    }
}