package com.ufabc.docchain.data

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonParseException

object FastApiResponseMapper {

    fun fromJSON(json: String): FastApiResponse? {
        val gson = Gson()
        val fixedJson = fixJsonFormat(json)

        try {
            return gson.fromJson(fixedJson, FastApiResponse::class.java)
        } catch (e: JsonParseException) {
            Log.e("FastApiResponseMapper", "Error mapping JSON to FastApiResponseMapper object.", e)
        }
        return null
    }

    private fun fixJsonFormat(json: String): String {
        return json.replace("=", ":").replace(Regex(":([^,}]*)"), ": \\\"\$1\\\"")
    }
}