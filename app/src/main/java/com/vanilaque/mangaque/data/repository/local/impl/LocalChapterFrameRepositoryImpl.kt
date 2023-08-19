package com.vanilaque.mangareader.data.repository.local.impl

import com.vanilaque.mangaque.data.db.MangaQueDatabase
import com.vanilaque.mangaque.data.model.ChapterFrame
import com.vanilaque.mangaque.data.model.ChapterWithFrames
import com.vanilaque.mangareader.data.repository.local.LocalChapterFrameRepository

class LocalChapterFrameRepositoryImpl(database: MangaQueDatabase): LocalChapterFrameRepository {
    private val dao = database.chapterFrameDao()
    override suspend fun insert(frame: ChapterFrame) {
        dao.insert(frame)
    }

    override suspend fun insertAll(frames: List<ChapterFrame>) {
        dao.insertAll(frames)
    }

    override suspend fun getFramesForChapter(chapterId: String): List<ChapterWithFrames> {
        return dao.getFramesForChapter(chapterId)
    }

    override suspend fun get(id: String): ChapterFrame {
        return dao.get(id)
    }

    override suspend fun clear() {
        dao.clear()
    }

    override suspend fun delete(frame: ChapterFrame) {
        dao.delete(frame)
    }

}