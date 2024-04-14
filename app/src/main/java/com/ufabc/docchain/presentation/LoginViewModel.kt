package com.ufabc.docchain.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ufabc.docchain.presentation.ActivityStatus.LOADING
import com.ufabc.docchain.presentation.LoginViewModelAction.ShowEmptyEmailInputToast
import com.ufabc.docchain.presentation.LoginViewModelAction.ShowEmptyPasswordInputToast

class LoginViewModel : ViewModel(), LoginI {

    private val _state = MutableLiveData<LoginViewModelState>()

    private val _action = MutableLiveData<LoginViewModelAction>()

    val state: LiveData<LoginViewModelState>
        get() = _state

    val action: LiveData<LoginViewModelAction>
        get() = _action

    init {
        _state.postValue(LoginViewModelState())
    }

    override fun submitLogin(email: String, password: String) {
        val success = validateInputs(email, password)

        if (success) {
            updateLoginStatus(LOADING)
        }
    }

    private fun validateInputs(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            postAction(ShowEmptyEmailInputToast)
            return false
        } else if (password.isEmpty()) {
            postAction(ShowEmptyPasswordInputToast)
            return false
        } else {
            return true
        }
    }

    private fun updateLoginStatus(status: ActivityStatus) {
        val currentState = _state.value ?: LoginViewModelState()
        val newState = currentState.copy(loginStatus = status)

        postState(newState)
    }

    private fun postState(newState: LoginViewModelState) {
        _state.postValue(newState)
    }

    private fun postAction(action: LoginViewModelAction) {
        _action.value = action
    }
}