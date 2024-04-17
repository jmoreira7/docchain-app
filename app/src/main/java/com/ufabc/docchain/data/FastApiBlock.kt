package com.ufabc.docchain.data

import com.google.gson.annotations.SerializedName

data class FastApiBlock(
    @SerializedName("index") val index: Int,
    @SerializedName("timestamp") val timestamp: String,
    @SerializedName("data") val examData: RawExam,
    @SerializedName("previous_hash") val previousHash: String,
    @SerializedName("nonce") val nonce: Int,
    @SerializedName("hash") val hash: String
)
