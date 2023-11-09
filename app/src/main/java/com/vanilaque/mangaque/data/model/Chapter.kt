package com.vanilaque.mangaque.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.vanilaque.mangaque.util.CHAPTER_DATABASE_TABLE

@Entity(
    tableName = CHAPTER_DATABASE_TABLE,
    foreignKeys = [ForeignKey(
        entity = Manga::class,
        parentColumns = ["id"],
        childColumns = ["mangaId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Chapter(
    @PrimaryKey
    val id: String,
    val mangaId: String,
    var index: Int,
    val title: String,
    val createdAt: Long?,
    val updatedAt: Long?,
)