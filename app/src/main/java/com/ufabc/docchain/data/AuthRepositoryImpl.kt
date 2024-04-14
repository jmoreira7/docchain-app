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
            val user = UserMapper.fromParams(name, id, email, authUid)

            cloudDbMechanism.insertUser(user)
        } else {
            false
        }
    }

    override suspend fun retrieveUserName(authUid: String): String {
        val userJson = cloudDbMechanism.retrieveUser(authUid).toString()
        val user = UserMapper.fromJSON(userJson)

        if (user != null) {
            return user.name
        }
        return EMPTY_STRING
    }

    companion object {
        private const val EMPTY_STRING = ""
    }
}