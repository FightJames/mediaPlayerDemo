package com.example.work.model

import com.google.gson.annotations.SerializedName

data class CastWrapper(
    @SerializedName("data") val postcastWrapper: Casts
)

data class Casts(
    @SerializedName("podcast") val list: List<Cast>
)

data class Cast(
    @SerializedName("artistName") val artistName: String = "",
    @SerializedName("artworkUrl100") val artworkUrl: String = "",
    @SerializedName("id") val id: String = "",
    @SerializedName("name") val name: String = ""
)

data class CastDetailsWrapper (
    @SerializedName("data") val castDetails:CastDetails
)

data class CastDetails(
    @SerializedName("collection") val collection: CastDetailsInfo
)

data class CastDetailsInfo(
    @SerializedName("artistId") val artistId: String,
    @SerializedName("artistName") val artistName: String,
    @SerializedName("artworkUrl100") val artworkSmallImageUrl: String,
    @SerializedName("artworkUrl600") val artworkLargeImageUrl: String,
    @SerializedName("collectionId") val id: String,
    @SerializedName("collectionName") val name: String,
    @SerializedName("contentFeed") val contents: List<Content>
)

data class Content(
    @SerializedName("contentUrl") val url: String,
    @SerializedName("desc") val desc: String,
    @SerializedName("publishedDate") val publishedDate: String,
    @SerializedName("title") val title: String
)
