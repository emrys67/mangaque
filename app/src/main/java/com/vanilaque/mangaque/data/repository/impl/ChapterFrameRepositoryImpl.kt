package com.vanilaque.mangaque.data.repository.impl

import com.vanilaque.mangaque.BuildConfig
import com.vanilaque.mangaque.api.MangaVerseApi
import com.vanilaque.mangaque.data.model.ChapterFrame
import com.vanilaque.mangaque.data.model.ChapterWithFrames
import com.vanilaque.mangaque.data.repository.ChapterFrameRepository
import com.vanilaque.mangaque.data.repository.local.LocalChapterFrameRepository
import com.vanilaque.mangaque.util.MANGA_QUE_HOST
import com.vanilaque.mangaque.util.toDbModel
import javax.inject.Inject

class ChapterFrameRepositoryImpl @Inject constructor(
    private val repo: LocalChapterFrameRepository,
    private val api: MangaVerseApi
) : ChapterFrameRepository {
    override suspend fun insert(frame: ChapterFrame) {
        repo.insert(frame)
    }

    override suspend fun insertAll(frames: List<ChapterFrame>) {
        repo.insertAll(frames)
    }

    override suspend fun getFramesForChapter(chapterId: String): ChapterWithFrames {
        return repo.getFramesForChapter(chapterId)
    }

    override suspend fun get(id: String): ChapterFrame {
        return repo.get(id)
    }

    override suspend fun clear() {
        repo.clear()
    }

    override suspend fun delete(frame: ChapterFrame) {
        repo.delete(frame)
    }

    override suspend fun fetchFromTheServer(chapterId: String): List<ChapterFrame> {
        val response = api.fetchImages(
            key = BuildConfig.API_KEY,
            host = MANGA_QUE_HOST,
            id = chapterId
        )
        return response.body()!!.data.map { it.toDbModel() }
    }

}