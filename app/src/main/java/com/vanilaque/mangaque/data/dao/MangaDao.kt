package com.vanilaque.mangaque.data.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.vanilaque.mangaque.data.model.Manga
import com.vanilaque.mangaque.data.model.MangaWithChapters
import com.vanilaque.mangaque.util.MANGA_DATABASE_TABLE

@Dao
interface MangaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(manga: Manga)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(manga: List<Manga>)

    @Update
    suspend fun update(manga: Manga)

    @Query("SELECT * FROM $MANGA_DATABASE_TABLE")
    suspend fun getAll(): List<Manga>

    @Query("SELECT * FROM $MANGA_DATABASE_TABLE WHERE isInFavorites = 1")
    fun getAllFavorite() : PagingSource<Int, Manga>

    @Query("SELECT * FROM $MANGA_DATABASE_TABLE WHERE downloaded = 1")
    fun getAllSaved(): PagingSource<Int, Manga>

    @Query("SELECT * FROM $MANGA_DATABASE_TABLE")
    fun getAllDataPaged(): PagingSource<Int, Manga>

    @Query("SELECT * FROM $MANGA_DATABASE_TABLE WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): Manga

    @Query("SELECT * FROM $MANGA_DATABASE_TABLE")
    suspend fun getAllMangaWithChapters(): List<MangaWithChapters>

    @Query("SELECT id FROM $MANGA_DATABASE_TABLE")
    suspend fun getAllIds(): List<String>

    @Query("SELECT * FROM $MANGA_DATABASE_TABLE WHERE id = :id")
    suspend fun getMangaWithChapters(id: String): MangaWithChapters

    @Query("DELETE FROM $MANGA_DATABASE_TABLE")
    suspend fun clear()

    @Query("DELETE FROM $MANGA_DATABASE_TABLE WHERE downloaded = 0 AND isInFavorites = 0")
    suspend fun clearTrash()

    @Delete
    suspend fun delete(manga: Manga)
}