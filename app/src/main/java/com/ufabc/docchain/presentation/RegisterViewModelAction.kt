package com.ufabc.docchain.presentation

sealed class RegisterViewModelAction {
    object ShowCreateUserSuccessToast : RegisterViewModelAction()
    object ShowCreateUserFailToast : RegisterViewModelAction()
    object ShowEmptyNameInputToast : RegisterViewModelAction()
    object ShowEmptyIdInputToast : RegisterViewModelAction()
    object ShowEmptyEmailInputToast : RegisterViewModelAction()
    object ShowEmptyPasswordInputToast : RegisterViewModelAction()
}