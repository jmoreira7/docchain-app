package com.ufabc.docchain.data

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonParseException

object UserMapper {
    fun fromParams(name: String, id: String, email: String, authId: String): User {
        return User(name, id, email, authId)
    }

    fun fromJSON(json: String): User? {
        val gson = Gson()

        try {
            return gson.fromJson(json, User::class.java)
        } catch (e: JsonParseException) {
            Log.e("UserMapper", "Error mapping JSON to User object.", e)
        }
        return null
    }
}