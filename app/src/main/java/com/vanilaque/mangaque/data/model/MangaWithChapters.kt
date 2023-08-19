package com.vanilaque.mangaque.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class MangaWithChapters(
    @Embedded
    val manga: Manga,
    @Relation(
        parentColumn = "id",
        entityColumn = "mangaId",
        entity = Chapter::class
    )
    val chapters: List<Chapter>
)
