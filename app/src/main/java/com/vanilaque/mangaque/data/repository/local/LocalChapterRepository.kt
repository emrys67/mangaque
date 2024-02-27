package com.vanilaque.mangaque.data.repository.local

import com.vanilaque.mangaque.data.model.Chapter
import com.vanilaque.mangaque.data.model.MangaWithChapters

interface LocalChapterRepository {
    suspend fun insert(chapter: Chapter)

    suspend fun insert(chapters: List<Chapter>)
    suspend fun getChaptersForManga(mangaSlug: String): MangaWithChapters

    suspend fun get(slug: String): Chapter

    suspend fun getByIndex(index: Int, mangaId: String): Chapter

    suspend fun clear()

    suspend fun delete(chapter: Chapter)
}