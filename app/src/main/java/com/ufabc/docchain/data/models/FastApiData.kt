package com.ufabc.docchain.data.models

import com.google.gson.annotations.SerializedName

data class FastApiData(
    @SerializedName("blocks") val blocks: List<FastApiBlock>
)
