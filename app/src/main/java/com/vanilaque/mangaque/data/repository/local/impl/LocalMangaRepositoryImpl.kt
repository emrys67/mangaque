package com.vanilaque.mangaque.data.repository.local.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vanilaque.mangaque.data.db.MangaQueDatabase
import com.vanilaque.mangaque.data.model.Manga
import com.vanilaque.mangaque.data.model.MangaWithChapters
import com.vanilaque.mangaque.data.repository.local.LocalMangaRepository
import kotlinx.coroutines.flow.Flow

class LocalMangaRepositoryImpl(database: MangaQueDatabase) : LocalMangaRepository {
    val dao = database.mangaDao()
    override suspend fun insert(manga: Manga) {
        dao.insert(manga)
    }

    override suspend fun insert(manga: List<Manga>) {
        dao.insert(manga)
    }

    override suspend fun update(manga: Manga) {
        dao.update(manga)
    }

    override suspend fun getAll(): List<Manga> {
        return dao.getAll()
    }

    override fun getAllDataPaged(): Flow<PagingData<Manga>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { dao.getAllDataPaged() }
        ).flow
    }

    override suspend fun get(id: String): Manga {
        return dao.getById(id)
    }

    override suspend fun getMangaWithChapters(id: String): MangaWithChapters {
        return dao.getMangaWithChapters(id)
    }

    override suspend fun getAllMangaWithChapters(): List<MangaWithChapters> {
        return dao.getAllMangaWithChapters()
    }

    override suspend fun getMangaIds(): List<String> {
        return dao.getAllIds()
    }

    override suspend fun clear() {
        dao.clear()
    }

    override suspend fun clearTrash() {
        dao.clearTrash()
    }

    override suspend fun delete(manga: Manga) {
        dao.delete(manga)
    }

}