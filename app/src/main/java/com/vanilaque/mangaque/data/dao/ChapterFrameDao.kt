package com.vanilaque.mangaque.data.dao

import androidx.room.*
import com.vanilaque.mangaque.data.model.Chapter
import com.vanilaque.mangaque.data.model.ChapterFrame
import com.vanilaque.mangaque.data.model.ChapterWithFrames
import com.vanilaque.mangaque.util.CHAPTER_DATABASE_TABLE
import com.vanilaque.mangaque.util.CHAPTER_FRAME_DATABASE_TABLE

@Dao
interface ChapterFrameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(frame: ChapterFrame)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(frames: List<ChapterFrame>)

    @Query("SELECT * FROM $CHAPTER_DATABASE_TABLE WHERE id = :chapterId")
    suspend fun getFramesForChapter(chapterId: String): ChapterWithFrames

    @Query("SELECT * FROM $CHAPTER_FRAME_DATABASE_TABLE WHERE id = :id LIMIT 1")
    suspend fun get(id: String): ChapterFrame

    @Query("DELETE FROM $CHAPTER_FRAME_DATABASE_TABLE")
    suspend fun clear()

    @Delete
    suspend fun delete(frame: ChapterFrame)
}