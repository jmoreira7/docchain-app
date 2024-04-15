package com.ufabc.docchain.presentation

sealed class LoginViewModelAction {
    object ShowEmptyEmailInputToast : LoginViewModelAction()
    object ShowEmptyPasswordInputToast : LoginViewModelAction()
    data class StartMenuActivity(val loggedInUserName: String) : LoginViewModelAction()
    object ShowFailAuthenticationToast : LoginViewModelAction()
}