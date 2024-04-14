package com.ufabc.docchain.data

class AuthRepositoryImpl : AuthRepositoryI {

    private val authMechanism = FirebaseAuthMechanism()

    private val cloudDbMechanism = FirebaseRTDatabaseMechanism()

    override suspend fun createUser(
        name: String,
        id: String,
        email: String,
        password: String
    ): Boolean {
        val authUid = authMechanism.createUser(email, password)

        return if (authUid.isNotEmpty()) {
            val user = User(
                name = name,
                id = id,
                email = email,
                authUid = authUid
            )

            cloudDbMechanism.insertUser(user)
        } else {
            false
        }
    }
}