package com.vanilaque.mangaque.data.dao

import androidx.room.*
import com.vanilaque.mangaque.data.model.Chapter
import com.vanilaque.mangaque.data.model.MangaWithChapters
import com.vanilaque.mangaque.util.CHAPTER_DATABASE_TABLE
import com.vanilaque.mangaque.util.MANGA_DATABASE_TABLE

@Dao
interface ChapterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(chapter: Chapter)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(chapter: List<Chapter>)

    @Query("SELECT * FROM $MANGA_DATABASE_TABLE WHERE id = :mangaId")
    suspend fun getChapterForManga(mangaId: String): MangaWithChapters

    @Query("SELECT * FROM $CHAPTER_DATABASE_TABLE WHERE id = :id LIMIT 1")
    suspend fun get(id: String): Chapter

    @Query("SELECT * FROM $CHAPTER_DATABASE_TABLE WHERE `index` = :index AND mangaId = :mangaId LIMIT 1")
    suspend fun getByOrder(index: Int, mangaId: String): Chapter

    @Query("DELETE FROM $CHAPTER_DATABASE_TABLE")
    suspend fun clear()

    @Delete
    suspend fun delete(chapter: Chapter)
}