package com.vanilaque.mangaque.data.repository.local

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.vanilaque.mangaque.data.model.Manga
import com.vanilaque.mangaque.data.model.MangaWithChapters
import kotlinx.coroutines.flow.Flow

interface LocalMangaRepository {
    suspend fun insert(manga: Manga)

    suspend fun insert(manga: List<Manga>)

    suspend fun update(manga: Manga)

    suspend fun getAll(): List<Manga>

    fun getAllDataPaged(): Flow<PagingData<Manga>>

    suspend fun get(id: String): Manga

    suspend fun getMangaWithChapters(id: String): MangaWithChapters

    suspend fun getAllMangaWithChapters(): List<MangaWithChapters>

    suspend fun getMangaIds(): List<String>

    suspend fun clear()

    suspend fun clearTrash()

    suspend fun delete(manga: Manga)
}