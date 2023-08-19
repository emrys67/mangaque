package com.vanilaque.mangaque.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vanilaque.mangaque.util.MANGA_DATABASE_TABLE

@Entity(
    tableName = MANGA_DATABASE_TABLE
)
data class Manga(
    @PrimaryKey
    val id: String,
    val title: String,
    val subTitle: String?,
    val status: String,
    val thumb: String,
    val summary: String,
    val authors: List<String>,
    val genres: List<String>,
    val nsfw: Boolean,
    val type: String,
    val createdAt: Long?,
    val updatedAt: Long?,
    var lastChapterRead: Int = 0,
    var lastOpenedAt: Long? = null,
    var addedToFavoritesAt: Long? = null,
    var isInFavorites: Boolean = false,
    var downloaded: Boolean = false
)