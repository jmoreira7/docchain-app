package com.ufabc.docchain.data

interface AuthRepositoryI {
    suspend fun createUser(name: String, id: String, email: String, password: String): Boolean
}