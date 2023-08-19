package com.vanilaque.mangareader.data.repository

import com.vanilaque.mangaque.data.model.Manga
import com.vanilaque.mangaque.data.model.MangaWithChapters

interface MangaRepository {
    suspend fun insert(manga: Manga)

    suspend fun insert(manga: List<Manga>)

    suspend fun getAll(): List<Manga>

    suspend fun get(id: String): Manga

    suspend fun getMangaWithChapters(id: String): MangaWithChapters

    suspend fun getAllMangaWithChapters(): List<MangaWithChapters>

    suspend fun getMangaIds(): List<String>

    suspend fun clear()

    suspend fun delete(manga: Manga)

    suspend fun fetchFromTheServer(page: String, genres: String): List<Manga>

    suspend fun fetchLatestFromTheServer(page: String, genres: String): List<Manga>

    suspend fun searchOnTheServer(name: String): List<Manga>

    suspend fun getById(id: String): Manga
}