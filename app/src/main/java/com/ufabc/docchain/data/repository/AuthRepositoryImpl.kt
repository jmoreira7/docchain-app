package com.ufabc.docchain.data.repository

import android.util.Log
import com.ufabc.docchain.data.mechanism.firebaseservices.FirebaseAuthMechanism
import com.ufabc.docchain.data.mechanism.firebaseservices.FirebaseRTDatabaseMechanism
import com.ufabc.docchain.data.models.User
import com.ufabc.docchain.data.mapper.UserMapper

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
            Log.d("DEBUG", "userJSON: [$userJSON]")
            val user = UserMapper.fromJSON(userJSON)
            Log.d("DEBUG", "User Kotlin Object: [$user]")
            val userAuthUid = user?.authUid ?: EMPTY_STRING
            Log.d("DEBUG", "userAuthUid: [$userAuthUid]")

            Result.success(userAuthUid)
        } else {
            Result.failure(Exception("User authentication fail."))
        }
    }

    override suspend fun retrieveUser(authUid: String): User? {
        val userJson = cloudDbMechanism.retrieveUserJSON(authUid).toString()
        val user = UserMapper.fromJSON(userJson)

        if (user != null) {
            return user
        }
        return null
    }

    companion object {
        private const val EMPTY_STRING = ""
    }
}