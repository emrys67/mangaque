package com.vanilaque.mangaque.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MultipleMangaResponse(
    val code: Int,
    val data: List<Manga>
)

@Serializable
data class MangaResponse(
    val code: Int,
    val data: Manga
)

@Serializable
data class ChaptersResponse(
    val code: Int,
    val data: List<Chapter>
)

@Serializable
data class ChapterFramesResponse(
    val code: Int,
    val data: List<ChapterFrame>
)

@Serializable
data class Manga(
    val id: String,
    val title: String,
    @SerialName("sub_title")
    val subTitle: String?,
    val status: String,
    val thumb: String,
    val summary: String,
    val authors: List<String>,
    val genres: List<String>,
    val nsfw: Boolean,
    val type: String,
    @SerialName("create_at")
    val createdAt: Long?,
    @SerialName("update_at")
    val updatedAt: Long?
)

@Serializable
data class Chapter(
    val id: String,
    val manga: String,
    val title: String,
    @SerialName("create_at")
    val createdAt: Long?,
    @SerialName("update_at")
    val updatedAt: Long?,
)

@Serializable
data class ChapterFrame(
    val id: String,
    val chapter: String,
    val manga: String,
    val index: Int,
    val link: String
)