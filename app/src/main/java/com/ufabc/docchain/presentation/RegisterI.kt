package com.ufabc.docchain.presentation

interface RegisterI {
    fun submitRegistration(name: String, id: String, email: String, password: String)
}