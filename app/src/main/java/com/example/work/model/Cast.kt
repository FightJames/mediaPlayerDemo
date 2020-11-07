package com.example.work.model

import com.google.gson.annotations.SerializedName

data class CastList(
    @SerializedName("data") val postcastWrapper: DataWrapper
)

data class DataWrapper(
    @SerializedName("podcast") val list: List<Cast>
)

data class Cast(
    @SerializedName("artistName") val artistName: String = "",
    @SerializedName("artworkUrl100") val artworkUrl: String = "",
    @SerializedName("id") val id: String = "",
    @SerializedName("name") val name: String = ""
)