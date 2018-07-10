package com.example.krasm.krasikovqrsurf.data

data class ReadData (
    val seq: Int,
    val data: String?,
    val error: String?
)

data class FirstJSON (
    val type: String,
    val symbol: Array<ReadData>
    )