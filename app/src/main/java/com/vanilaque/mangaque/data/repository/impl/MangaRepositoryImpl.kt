package com.vanilaque.mangaque.data.repository.impl

import androidx.paging.PagingData
import com.vanilaque.mangaque.BuildConfig
import com.vanilaque.mangaque.api.MangaVerseApi
import com.vanilaque.mangaque.data.model.Manga
import com.vanilaque.mangaque.data.model.MangaWithChapters
import com.vanilaque.mangaque.data.repository.MangaRepository
import com.vanilaque.mangaque.data.repository.local.LocalMangaRepository
import com.vanilaque.mangaque.util.MANGA_QUE_HOST
import com.vanilaque.mangaque.util.toDbModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MangaRepositoryImpl @Inject constructor(
    private val api: MangaVerseApi,
    private val repo: LocalMangaRepository
) : MangaRepository {
    override suspend fun insert(manga: Manga) {
        repo.insert(manga)
    }

    override suspend fun insert(manga: List<Manga>) {
        repo.insert(manga)
    }

    override suspend fun update(manga: Manga) {
        repo.update(manga)
    }

    override suspend fun getAll(): List<Manga> {
        return repo.getAll()
    }

    override fun getAllFavoritePaged(): Flow<PagingData<Manga>> {
        return repo.getAllFavoritePaged()
    }

    override fun getAllSavedPaged(): Flow<PagingData<Manga>> {
        return repo.getAllSavedPaged()
    }


    override fun getAllPaged(): Flow<PagingData<Manga>> {
        return repo.getAllPaged()
    }

    override suspend fun get(id: String): Manga {
        return repo.get(id)
    }

    override suspend fun getMangaWithChapters(id: String): MangaWithChapters {
        return repo.getMangaWithChapters(id)
    }

    override suspend fun getAllMangaWithChapters(): List<MangaWithChapters> {
        return repo.getAllMangaWithChapters()
    }

    override suspend fun getMangaIds(): List<String> {
        return repo.getMangaIds()
    }

    override suspend fun clear() {
        repo.clear()
    }

    override suspend fun clearTrash() {
        repo.clearTrash()
    }

    override suspend fun delete(manga: Manga) {
        repo.delete(manga)
    }

    override suspend fun fetchFromTheServer(page: String, genres: String): List<Manga> {
        val response = api.fetchManga(
            key = BuildConfig.API_KEY,
            host = MANGA_QUE_HOST,
            page = page,
            genres = genres
        )
        return response.body()!!.data.map { it.toDbModel() }
    }

    override suspend fun fetchLatestFromTheServer(page: String, genres: String): List<Manga> {
        val response = api.fetchLatest(
            key = BuildConfig.API_KEY,
            host = MANGA_QUE_HOST,
            page = page,
            genres = genres
        )
        return response.body()!!.data.map { it.toDbModel() }
    }

    override suspend fun searchOnTheServer(name: String): List<Manga> {
        val response = api.searchManga(
            key = BuildConfig.API_KEY,
            host = MANGA_QUE_HOST,
            name = name
        )
        return response.body()!!.data.map { it.toDbModel() }
    }

    override suspend fun getById(id: String): Manga {
        val response = api.getManga(
            key = BuildConfig.API_KEY,
            host = MANGA_QUE_HOST,
            id = id
        )
        return response.body()!!.data.toDbModel()
    }
}