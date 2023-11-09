package com.vanilaque.mangareader.data.repository.impl

import com.vanilaque.mangaque.api.MangaVerseApi
import com.vanilaque.mangaque.data.model.Chapter
import com.vanilaque.mangaque.data.model.MangaWithChapters
import com.vanilaque.mangaque.util.MANGA_QUE_HOST
import com.vanilaque.mangaque.util.MANGA_QUE_KEY
import com.vanilaque.mangaque.util.toDbModel
import com.vanilaque.mangareader.data.repository.ChapterRepository
import com.vanilaque.mangaque.data.repository.local.LocalChapterRepository
import javax.inject.Inject

class ChapterRepositoryImpl @Inject constructor(
    private val repo: LocalChapterRepository,
    private val api: MangaVerseApi
) : ChapterRepository {
    override suspend fun insert(chapter: Chapter) {
        repo.insert(chapter)
    }

    override suspend fun insert(chapters: List<Chapter>) {
        repo.insert(chapters)
    }

    override suspend fun getChaptersForManga(id: String): MangaWithChapters {
        return repo.getChaptersForManga(id)
    }

    override suspend fun get(id: String): Chapter {
        return repo.get(id)
    }

    override suspend fun getByIndex(index: Int, mangaId: String): Chapter {
        return repo.getByIndex(index, mangaId)
    }

    override suspend fun clear() {
        repo.clear()
    }

    override suspend fun delete(chapter: Chapter) {
        repo.delete(chapter)
    }

    override suspend fun fetchFromTheServer(mangaId: String): List<Chapter> {
        val response = api.fetchChapters(
            key = MANGA_QUE_KEY,
            host = MANGA_QUE_HOST,
            id = mangaId
        )
        return response.body()!!.data.map { it.toDbModel() }
    }

}