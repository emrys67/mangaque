package com.vanilaque.mangareader.data.repository.local

import com.vanilaque.mangaque.data.model.Chapter
import com.vanilaque.mangaque.data.model.MangaWithChapters

interface LocalChapterRepository {
    suspend fun insert(chapter: Chapter)

    suspend fun insert(chapters: List<Chapter>)
    suspend fun getChaptersForManga(mangaSlug: String): MangaWithChapters

    suspend fun get(slug: String): Chapter

    suspend fun clear()

    suspend fun delete(chapter: Chapter)
}