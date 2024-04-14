package com.ufabc.docchain.data

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("name") val name: String,
    @SerializedName("id") val id: String,
    @SerializedName("email") val email: String,
    @SerializedName("authUid") val authUid: String,
)