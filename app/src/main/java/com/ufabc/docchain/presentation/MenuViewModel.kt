package com.ufabc.docchain.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ufabc.docchain.data.AuthRepositoryI
import com.ufabc.docchain.data.AuthRepositoryImpl
import com.ufabc.docchain.data.User
import kotlinx.coroutines.launch

class MenuViewModel: ViewModel(), MenuEvent {

    private val authRepository: AuthRepositoryI = AuthRepositoryImpl()

    private val _state = MutableLiveData<MenuViewModelState>()

    private var userId: String = EMPTY_STRING

    val state: LiveData<MenuViewModelState>
        get() = _state

    override fun setUserAuthUid(authUid: String) {
        viewModelScope.launch {
            val user = authRepository.retrieveUser(authUid)

            user?.let {
                userId = it.id
            }

            Log.d(LOG_TAG, "User Id set as [$userId]")
        }
    }

    private fun postState(newState: MenuViewModelState?) {
        if (newState != null) {
            _state.postValue(newState)
        }
    }

    companion object {
        private const val LOG_TAG = "MenuViewModel"

        private const val EMPTY_STRING = ""
    }
}