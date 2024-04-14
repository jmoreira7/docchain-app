package com.ufabc.docchain.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ufabc.docchain.data.FirebaseAuthRepository
import com.ufabc.docchain.presentation.RegisterViewModel.RegisterViewModelAction.showCreateUserFailToast
import com.ufabc.docchain.presentation.RegisterViewModel.RegisterViewModelAction.showCreateUserSuccessToast
import com.ufabc.docchain.presentation.RegisterViewModel.RegisterViewModelAction.showEmptyEmailInputToast
import com.ufabc.docchain.presentation.RegisterViewModel.RegisterViewModelAction.showEmptyIdInputToast
import com.ufabc.docchain.presentation.RegisterViewModel.RegisterViewModelAction.showEmptyNameInputToast
import com.ufabc.docchain.presentation.RegisterViewModel.RegisterViewModelAction.showEmptyPasswordInputToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel() : ViewModel(), RegisterActivity.RegisterInterface {

    private val authRepository: FirebaseAuthRepository = FirebaseAuthRepository()

    private val _action = MutableLiveData<RegisterViewModelAction>()

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
            postAction(showEmptyNameInputToast)
            false
        } else if (id.isEmpty()) {
            postAction(showEmptyIdInputToast)
            false
        } else if (email.isEmpty()) {
            postAction(showEmptyEmailInputToast)
            false
        } else if (password.isEmpty()) {
            postAction(showEmptyPasswordInputToast)
            false
        } else
            true
    }

    private fun createUser(email: String, password: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val success = authRepository.createUser(email, password)

            if (success) {
                postAction(showCreateUserSuccessToast)
            } else {
                postAction(showCreateUserFailToast)
            }
        }
    }

    private fun postAction(action: RegisterViewModelAction) {
        _action.value = action
    }

    sealed class RegisterViewModelAction() {
        object showCreateUserSuccessToast : RegisterViewModelAction()
        object showCreateUserFailToast : RegisterViewModelAction()
        object showEmptyNameInputToast : RegisterViewModelAction()
        object showEmptyIdInputToast : RegisterViewModelAction()
        object showEmptyEmailInputToast : RegisterViewModelAction()
        object showEmptyPasswordInputToast : RegisterViewModelAction()
    }

}