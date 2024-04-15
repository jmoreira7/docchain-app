package com.ufabc.docchain.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.RuntimeException

class FirebaseAuthMechanism {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()


    suspend fun createUser(email: String, password: String): String {
        return withContext(Dispatchers.IO) {
            val deferred = CompletableDeferred<String>()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(LOG_TAG, "createUserWithEmail:success")
                        val uid = auth.currentUser?.uid ?: ""
                        deferred.complete(uid)
                    } else {
                        Log.w(LOG_TAG, "createUserWithEmail:failure", task.exception)
                        deferred.complete("")
                    }
                }
            deferred.await()
        }
    }

    suspend fun signIn(email: String, password: String): Result<String> {
        return withContext(Dispatchers.IO) {
            val deferred = CompletableDeferred<Result<String>>()

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userUid = auth.currentUser?.uid

                    if (userUid != null) {
                        deferred.complete(Result.success(userUid))
                    }

                    deferred.complete(Result.success(EMPTY_STRING))

                    Log.d(LOG_TAG, "signInWithEmail:success")
                } else {
                    task.exception?.let {
                        deferred.complete(Result.failure(it))
                    }

                    deferred.complete(Result.failure(Exception("User authentication fail.")))

                    Log.w(LOG_TAG, "signInWithEmail:failure", task.exception)
                }
            }
            deferred.await()
        }
    }

    companion object {
        private const val LOG_TAG = "FirebaseAuthRepository"
        private const val EMPTY_STRING = ""
    }
}