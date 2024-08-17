package com.ufabc.docchain.presentation.login

sealed class LoginViewModelAction {
    object ShowEmptyEmailInputToast : LoginViewModelAction()
    object ShowEmptyPasswordInputToast : LoginViewModelAction()
    data class StartMenuActivity(val loggedInUserName: String) : LoginViewModelAction()
    object ShowFailAuthenticationToast : LoginViewModelAction()
}