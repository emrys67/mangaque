package com.vanilaque.mangareader.data.repository

import androidx.paging.PagingData
import com.vanilaque.mangaque.data.model.Manga
import com.vanilaque.mangaque.data.model.MangaWithChapters
import kotlinx.coroutines.flow.Flow

interface MangaRepository {
    suspend fun insert(manga: Manga)

    suspend fun insert(manga: List<Manga>)

    suspend fun update(manga: Manga)

    suspend fun getAll(): List<Manga>

    fun getAllFavoritePaged(): Flow<PagingData<Manga>>

    fun getAllSavedPaged(): Flow<PagingData<Manga>>

    fun getAllPaged(): Flow<PagingData<Manga>>

    suspend fun get(id: String): Manga

    suspend fun getMangaWithChapters(id: String): MangaWithChapters

    suspend fun getAllMangaWithChapters(): List<MangaWithChapters>

    suspend fun getMangaIds(): List<String>

    suspend fun clear()

    suspend fun clearTrash()

    suspend fun delete(manga: Manga)

    suspend fun fetchFromTheServer(page: String, genres: String): List<Manga>

    suspend fun fetchLatestFromTheServer(page: String, genres: String): List<Manga>

    suspend fun searchOnTheServer(name: String): List<Manga>

    suspend fun getById(id: String): Manga
}