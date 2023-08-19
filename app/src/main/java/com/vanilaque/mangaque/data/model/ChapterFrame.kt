package com.vanilaque.mangaque.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.vanilaque.mangaque.util.CHAPTER_FRAME_DATABASE_TABLE

@Entity(
    tableName = CHAPTER_FRAME_DATABASE_TABLE,
    foreignKeys = [ForeignKey(
        entity = Chapter::class,
        parentColumns = ["id"],
        childColumns = ["chapterId"],
        onDelete = ForeignKey.CASCADE
    ),
        ForeignKey(
            entity = Manga::class,
            parentColumns = ["id"],
            childColumns = ["mangaId"],
            onDelete = ForeignKey.CASCADE
        )]
)
data class ChapterFrame(
    @PrimaryKey
    val id: String,
    val chapterId: String,
    val mangaId: String,
    val index: Int,
    val link: String
)