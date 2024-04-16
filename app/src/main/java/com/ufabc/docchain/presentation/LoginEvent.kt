package com.ufabc.docchain.presentation

interface LoginEvent {
    fun submitLogin(email: String, password: String)
}