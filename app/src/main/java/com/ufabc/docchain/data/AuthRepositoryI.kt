package com.ufabc.docchain.data

interface AuthRepositoryI {
    suspend fun createUser(name: String, id: String, email: String, password: String): Boolean

    suspend fun signIn(email: String, password: String): Result<String>

    suspend fun retrieveUser(authUid: String): User?
}