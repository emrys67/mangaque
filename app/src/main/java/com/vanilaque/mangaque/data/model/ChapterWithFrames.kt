package com.vanilaque.mangaque.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class ChapterWithFrames(
    @Embedded
    val chapter: Chapter,
    @Relation(
        parentColumn = "id",
        entityColumn = "chapterId",
        entity = ChapterFrame::class
    )
    val frames: List<ChapterFrame>
)
