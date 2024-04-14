package com.ufabc.docchain.data

import com.google.firebase.database.FirebaseDatabase
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
                    deferred.complete(true)
                } else {
                    deferred.complete(false)
                }
            }
            deferred.await()
        }
    }

    companion object {
        private const val CHILD_NODE = "Users"
    }
}