package com.ufabc.docchain.presentation

sealed class InsertExamViewModelAction {
    object ShowSuccessDialog : InsertExamViewModelAction()
    object ShowFailDialog : InsertExamViewModelAction()
}