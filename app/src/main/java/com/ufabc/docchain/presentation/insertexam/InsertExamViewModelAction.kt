package com.ufabc.docchain.presentation.insertexam

sealed class InsertExamViewModelAction {
    object ShowSuccessToast : InsertExamViewModelAction()
    object ShowFailToast : InsertExamViewModelAction()
    object ShowEmptyInputFieldToast : InsertExamViewModelAction()
}