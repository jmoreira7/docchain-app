package com.ufabc.docchain.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ufabc.docchain.data.FirebaseAuthRepository
import com.ufabc.docchain.presentation.RegisterViewModel.RegisterViewModelAction.showCreateUserFailDialog
import com.ufabc.docchain.presentation.RegisterViewModel.RegisterViewModelAction.showCreateUserSuccessDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel() : ViewModel(), RegisterActivity.RegisterInterface {

    private val authRepository: FirebaseAuthRepository = FirebaseAuthRepository()

    private val _action = MutableLiveData<RegisterViewModelAction>()

    val action: LiveData<RegisterViewModelAction>
        get() = _action

    override fun createUser(email: String, password: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val success = authRepository.createUser(email, password)

            if (success) {
                postAction(showCreateUserSuccessDialog)
            } else {
                postAction(showCreateUserFailDialog)
            }
        }
    }

    private fun postAction(action: RegisterViewModelAction) {
        _action.value = action
    }

    sealed class RegisterViewModelAction() {
        object showCreateUserSuccessDialog : RegisterViewModelAction()

        object showCreateUserFailDialog : RegisterViewModelAction()
    }

}