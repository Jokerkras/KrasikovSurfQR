package com.example.krasm.krasikovqrsurf.data

import com.google.gson.annotations.SerializedName

data class Symbol (
        @SerializedName("seq")val seq: Int,
        @SerializedName("data")val data: String?,
        @SerializedName("error")val error: String?
)

data class FirstJSON (
        @SerializedName("type") val type: String,
        @SerializedName("symbol") val symbol: List<Symbol>
    )