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

    override suspend fun signIn(email: String, password: String): Result<String> {
        val result = authMechanism.signIn(email, password)

        return if (result.isSuccess) {
            val userJSON = cloudDbMechanism.retrieveUserJSON(result.getOrElse { EMPTY_STRING })
                .toString()
            val user = UserMapper.fromJSON(userJSON)
            val userName = user?.name ?: EMPTY_STRING

            Result.success(userName)
        } else {
            Result.failure(Exception("User authentication fail."))
        }
    }

    override suspend fun retrieveUserName(authUid: String): String {
        val userJson = cloudDbMechanism.retrieveUserJSON(authUid).toString()
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