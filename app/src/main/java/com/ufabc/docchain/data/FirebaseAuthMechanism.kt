package com.ufabc.docchain.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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

    companion object {
        private const val LOG_TAG = "FirebaseAuthRepository"
    }
}