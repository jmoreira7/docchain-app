package com.ufabc.docchain.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ufabc.docchain.data.AuthRepositoryI
import com.ufabc.docchain.data.AuthRepositoryImpl
import com.ufabc.docchain.data.FirebaseAuthMechanism
import com.ufabc.docchain.presentation.ActivityStatus.LOADING
import com.ufabc.docchain.presentation.ActivityStatus.NORMAL
import com.ufabc.docchain.presentation.RegisterViewModelAction.ShowCreateUserFailToast
import com.ufabc.docchain.presentation.RegisterViewModelAction.ShowCreateUserSuccessToast
import com.ufabc.docchain.presentation.RegisterViewModelAction.ShowEmptyEmailInputToast
import com.ufabc.docchain.presentation.RegisterViewModelAction.ShowEmptyIdInputToast
import com.ufabc.docchain.presentation.RegisterViewModelAction.ShowEmptyNameInputToast
import com.ufabc.docchain.presentation.RegisterViewModelAction.ShowEmptyPasswordInputToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel() : ViewModel(), RegisterI {

    private val authRepository: AuthRepositoryI = AuthRepositoryImpl()

    private val _state = MutableLiveData<RegisterViewModelState>()

    private val _action = MutableLiveData<RegisterViewModelAction>()

    val state: LiveData<RegisterViewModelState>
        get() = _state

    val action: LiveData<RegisterViewModelAction>
        get() = _action

    init {
        _state.postValue(RegisterViewModelState())
    }

    override fun submitRegistration(name: String, id: String, email: String, password: String) {
        val success = validateInputs(name, id, email, password)

        if (success) {
            createUser(name, id, email, password)
        }
    }

    private fun validateInputs(name: String, id: String, email: String, password: String): Boolean {
        return if (name.isEmpty()) {
            postAction(ShowEmptyNameInputToast)
            false
        } else if (id.isEmpty()) {
            postAction(ShowEmptyIdInputToast)
            false
        } else if (email.isEmpty()) {
            postAction(ShowEmptyEmailInputToast)
            false
        } else if (password.isEmpty()) {
            postAction(ShowEmptyPasswordInputToast)
            false
        } else
            true
    }

    private fun createUser(name: String, id: String, email: String, password: String) {
        updateRegisterStatus(LOADING)

        CoroutineScope(Dispatchers.Main).launch {
            val success = authRepository.createUser(name, id, email, password)

            if (success) {
                postAction(ShowCreateUserSuccessToast)
            } else {
                postAction(ShowCreateUserFailToast)
            }

            updateRegisterStatus(NORMAL)
        }
    }

    private fun updateRegisterStatus(status: ActivityStatus) {
        val currentState = _state.value ?: RegisterViewModelState()
        val newState = currentState.copy(registerStatus = status)

        postState(newState)
    }

    private fun postState(newState: RegisterViewModelState?) {
        if (newState != null) {
            _state.postValue(newState)
        }
    }

    private fun postAction(action: RegisterViewModelAction) {
        _action.value = action
    }
}