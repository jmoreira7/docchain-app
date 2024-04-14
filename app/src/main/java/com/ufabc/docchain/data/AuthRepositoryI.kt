package com.ufabc.docchain.data

interface AuthRepositoryI {
    suspend fun createUser(email: String, password: String): Boolean
}