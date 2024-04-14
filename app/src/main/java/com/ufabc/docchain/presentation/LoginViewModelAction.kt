package com.ufabc.docchain.presentation

sealed class LoginViewModelAction {
    object ShowEmptyEmailInputToast : LoginViewModelAction()
    object ShowEmptyPasswordInputToast : LoginViewModelAction()
}