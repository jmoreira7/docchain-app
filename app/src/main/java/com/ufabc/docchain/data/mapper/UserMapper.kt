package com.ufabc.docchain.data.mapper

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.ufabc.docchain.data.models.User

object UserMapper {
    fun fromParams(name: String, id: String, email: String, authId: String): User {
        return User(name, id, email, authId)
    }

    fun fromJSON(json: String): User? {
        val gson = Gson()
        val fixedJson = fixJsonFormat(json)
        Log.d("DEBUG", "fixedJson: [$fixedJson]")

        try {
            return gson.fromJson(fixedJson, User::class.java)
        } catch (e: JsonParseException) {
            Log.e("UserMapper", "Error mapping JSON to User object.", e)
        }
        return null
    }

    private fun fixJsonFormat(json: String): String {
        return json.replace("=", ":").replace(Regex(":([^,}]*)"), ": \\\"\$1\\\"")
    }
}