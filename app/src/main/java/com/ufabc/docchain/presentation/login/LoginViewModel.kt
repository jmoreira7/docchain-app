package com.ufabc.docchain.presentation.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ufabc.docchain.data.repository.AuthRepositoryI
import com.ufabc.docchain.data.repository.AuthRepositoryImpl
import com.ufabc.docchain.presentation.shared.ActivityStatus
import com.ufabc.docchain.presentation.shared.ActivityStatus.LOADING
import com.ufabc.docchain.presentation.shared.ActivityStatus.NORMAL
import com.ufabc.docchain.presentation.login.LoginViewModelAction.*
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel(), LoginEvent {

    private val authRepository: AuthRepositoryI = AuthRepositoryImpl()

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
            viewModelScope.launch {
                val result = authRepository.signIn(email, password)

                if (result.isSuccess) {
                    val userAuthUid = result.getOrElse { UNKNOWN_USER_STRING }
                    Log.d("DEBUG", "Logged in user name: [$userAuthUid]")

                    postAction(StartMenuActivity(userAuthUid))
                } else {
                    postAction(ShowFailAuthenticationToast)
                }

                updateLoginStatus(NORMAL)
            }
        }
    }

    private fun validateInputs(email: String, password: String): Boolean {
        return if (email.isEmpty()) {
            postAction(ShowEmptyEmailInputToast)
            false
        } else if (password.isEmpty()) {
            postAction(ShowEmptyPasswordInputToast)
            false
        } else {
            true
        }
    }

    private fun updateLoginStatus(status: ActivityStatus) {
        val currentState = _state.value ?: LoginViewModelState()
        val newState = currentState.copy(loginStatus = status)

        postState(newState)
    }

    private fun postState(newState: LoginViewModelState?) {
        if (newState != null) {
            _state.postValue(newState)
        }
    }

    private fun postAction(action: LoginViewModelAction) {
        _action.value = action
    }

    companion object {
        private const val UNKNOWN_USER_STRING = "usuário não identificado"

        private const val EMPTY_STRING = ""
    }
}