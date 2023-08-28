package com.vanilaque.mangaque.data.repository.local

import com.vanilaque.mangaque.data.model.ChapterFrame
import com.vanilaque.mangaque.data.model.ChapterWithFrames

interface LocalChapterFrameRepository {
    suspend fun insert(frame: ChapterFrame)

    suspend fun insertAll(frames: List<ChapterFrame>)

    suspend fun getFramesForChapter(chapterId: String): ChapterWithFrames

    suspend fun get(id: String): ChapterFrame

    suspend fun clear()

    suspend fun delete(frame: ChapterFrame)
}