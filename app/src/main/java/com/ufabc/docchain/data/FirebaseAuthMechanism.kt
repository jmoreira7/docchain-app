package com.ufabc.docchain.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FirebaseAuthMechanism {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()


    suspend fun createUser(email: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            val deferred = CompletableDeferred<Boolean>()
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(LOG_TAG, "createUserWithEmail:success")
                        deferred.complete(true)
                    } else {
                        Log.w(LOG_TAG, "createUserWithEmail:failure", task.exception)
                        deferred.complete(false)
                    }
                }
            deferred.await()
        }
    }

    companion object {
        private const val LOG_TAG = "FirebaseAuthRepository"
    }
}