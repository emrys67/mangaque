package com.vanilaque.mangareader.data.repository

import com.vanilaque.mangaque.data.model.ChapterFrame
import com.vanilaque.mangaque.data.model.ChapterWithFrames

interface ChapterFrameRepository {
    suspend fun insert(frame: ChapterFrame)

    suspend fun insertAll(frames: List<ChapterFrame>)

    suspend fun getFramesForChapter(chapterId: String): ChapterWithFrames

    suspend fun get(id: String): ChapterFrame

    suspend fun clear()

    suspend fun delete(frame: ChapterFrame)

    suspend fun fetchFromTheServer(chapterId: String): List<ChapterFrame>
}