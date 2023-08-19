package com.vanilaque.mangareader.data.repository

import com.vanilaque.mangaque.data.model.Chapter
import com.vanilaque.mangaque.data.model.MangaWithChapters

interface ChapterRepository {
    suspend fun insert(chapter: Chapter)

    suspend fun insert(chapters: List<Chapter>)

    suspend fun getChaptersForManga(id: String): MangaWithChapters

    suspend fun get(id: String): Chapter

    suspend fun clear()

    suspend fun delete(chapter: Chapter)

    suspend fun fetchFromTheServer(mangaId: String): List<Chapter>
}