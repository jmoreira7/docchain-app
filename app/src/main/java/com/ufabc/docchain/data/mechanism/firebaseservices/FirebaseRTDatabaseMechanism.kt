package com.ufabc.docchain.data.mechanism.firebaseservices

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.ufabc.docchain.data.models.User
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FirebaseRTDatabaseMechanism {

    private val database = FirebaseDatabase.getInstance()

    private val usersRef = database.getReference(CHILD_NODE)

    suspend fun insertUser(user: User): Boolean {
        return withContext(Dispatchers.IO) {
            val deferred = CompletableDeferred<Boolean>()

            usersRef.child(user.authUid).setValue(user).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(LOG_TAG, "Insert user succeed. user: [$user]")
                    deferred.complete(true)
                } else {
                    Log.d(LOG_TAG, "Failed to insert user.")
                    deferred.complete(false)
                }
            }
            deferred.await()
        }
    }

    suspend fun retrieveUserJSON(authUid: String): Any? {
        return withContext(Dispatchers.IO) {
            val deferred = CompletableDeferred<Any?>()

            usersRef.child(authUid).get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val dataSnapshot = task.result
                        val value = dataSnapshot.value

                        Log.d(LOG_TAG, "Retrieve user succeed. user: [$value]")

                        deferred.complete(value)
                    } else {
                        val exception = task.exception

                        Log.w(LOG_TAG, "Failed to retrieve user.", exception)

                        deferred.complete(null)
                    }
                }
            deferred.await()
        }
    }

    companion object {
        private const val LOG_TAG = "FirebaseRTDatabaseMechanism"
        private const val CHILD_NODE = "Users"
    }
}