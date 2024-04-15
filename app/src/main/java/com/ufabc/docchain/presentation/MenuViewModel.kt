package com.ufabc.docchain.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ufabc.docchain.data.AuthRepositoryI
import com.ufabc.docchain.data.AuthRepositoryImpl

class MenuViewModel: ViewModel(), MenuI {

    private val authRepository: AuthRepositoryI = AuthRepositoryImpl()

    private val _state = MutableLiveData<MenuViewModelState>()

    val state: LiveData<MenuViewModelState>
        get() = _state

    private fun postState(newState: MenuViewModelState?) {
        if (newState != null) {
            _state.postValue(newState)
        }
    }
}