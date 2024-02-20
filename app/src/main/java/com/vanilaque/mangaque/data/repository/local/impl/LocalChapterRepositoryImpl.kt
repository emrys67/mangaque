package com.vanilaque.mangaque.data.repository.local.impl

import com.vanilaque.mangaque.data.db.MangaQueDatabase
import com.vanilaque.mangaque.data.model.Chapter
import com.vanilaque.mangaque.data.model.MangaWithChapters
import com.vanilaque.mangaque.data.repository.local.LocalChapterRepository

class LocalChapterRepositoryImpl(database: MangaQueDatabase) : LocalChapterRepository {
    private val dao = database.chapterDao()
    override suspend fun insert(chapter: Chapter) {
        dao.insert(chapter)
    }

    override suspend fun insert(chapters: List<Chapter>) {
        dao.insertAll(chapters)
    }

    override suspend fun getChaptersForManga(id: String): MangaWithChapters {
        return dao.getChapterForManga(id)
    }

    override suspend fun get(id: String): Chapter {
        return dao.get(id)
    }

    override suspend fun getByIndex(index: Int, mangaId: String): Chapter {
        return dao.getByOrder(index, mangaId)
    }

    override suspend fun clear() {
        dao.clear()
    }

    override suspend fun delete(chapter: Chapter) {
        dao.delete(chapter)
    }
}