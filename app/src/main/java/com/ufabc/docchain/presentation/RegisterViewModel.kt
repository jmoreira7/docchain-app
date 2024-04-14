package com.ufabc.docchain.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ufabc.docchain.data.FirebaseAuthRepository
import com.ufabc.docchain.presentation.ActivityStatus.LOADING
import com.ufabc.docchain.presentation.ActivityStatus.NORMAL
import com.ufabc.docchain.presentation.RegisterViewModel.RegisterViewModelAction.ShowCreateUserFailToast
import com.ufabc.docchain.presentation.RegisterViewModel.RegisterViewModelAction.ShowCreateUserSuccessToast
import com.ufabc.docchain.presentation.RegisterViewModel.RegisterViewModelAction.ShowEmptyEmailInputToast
import com.ufabc.docchain.presentation.RegisterViewModel.RegisterViewModelAction.ShowEmptyIdInputToast
import com.ufabc.docchain.presentation.RegisterViewModel.RegisterViewModelAction.ShowEmptyNameInputToast
import com.ufabc.docchain.presentation.RegisterViewModel.RegisterViewModelAction.ShowEmptyPasswordInputToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel() : ViewModel(), RegisterI {

    private val authRepository: FirebaseAuthRepository = FirebaseAuthRepository()

    private val _state = MutableLiveData<RegisterViewModelState>()

    private val _action = MutableLiveData<RegisterViewModelAction>()

    val state: LiveData<RegisterViewModelState>
        get() = _state

    val action: LiveData<RegisterViewModelAction>
        get() = _action

    override fun submitRegistration(name: String, id: String, email: String, password: String) {
        val success = validateInputs(name, id, email, password)

        if (success) {
            createUser(email, password)
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

    private fun createUser(email: String, password: String) {
        CoroutineScope(Dispatchers.Main).launch {
            var newState = _state.value?.copy(registerStatus = LOADING)
            postState(newState)

            val success = authRepository.createUser(email, password)

            if (success) {
                postAction(ShowCreateUserSuccessToast)
            } else {
                postAction(ShowCreateUserFailToast)
            }

            newState = _state.value?.copy(registerStatus = NORMAL)
            postState(newState)
        }
    }

    private fun postState(newState: RegisterViewModelState?) {
        if (newState != null) {
            _state.postValue(newState)
        }
    }

    private fun postAction(action: RegisterViewModelAction) {
        _action.value = action
    }

    sealed class RegisterViewModelAction() {
        object ShowCreateUserSuccessToast : RegisterViewModelAction()
        object ShowCreateUserFailToast : RegisterViewModelAction()
        object ShowEmptyNameInputToast : RegisterViewModelAction()
        object ShowEmptyIdInputToast : RegisterViewModelAction()
        object ShowEmptyEmailInputToast : RegisterViewModelAction()
        object ShowEmptyPasswordInputToast : RegisterViewModelAction()
    }

}