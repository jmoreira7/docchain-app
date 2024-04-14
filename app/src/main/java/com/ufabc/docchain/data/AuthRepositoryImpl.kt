package com.ufabc.docchain.data

class AuthRepositoryImpl : AuthRepositoryI {

    private val authMechanism = FirebaseAuthMechanism()

    override suspend fun createUser(email: String, password: String): Boolean {
        return authMechanism.createUser(email, password)
    }
}