package com.ufabc.docchain.presentation.register

interface RegisterEvent {
    fun submitRegistration(name: String, id: String, email: String, password: String)
}