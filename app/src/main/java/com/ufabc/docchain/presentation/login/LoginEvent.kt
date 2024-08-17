package com.ufabc.docchain.presentation.login

interface LoginEvent {
    fun submitLogin(email: String, password: String)
}