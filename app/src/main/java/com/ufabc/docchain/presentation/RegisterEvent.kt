package com.ufabc.docchain.presentation

interface RegisterEvent {
    fun submitRegistration(name: String, id: String, email: String, password: String)
}