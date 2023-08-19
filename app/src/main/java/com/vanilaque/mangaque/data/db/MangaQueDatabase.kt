package com.vanilaque.mangaque.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vanilaque.mangaque.data.dao.ChapterDao
import com.vanilaque.mangaque.data.dao.ChapterFrameDao
import com.vanilaque.mangaque.data.dao.MangaDao
import com.vanilaque.mangaque.data.model.Chapter
import com.vanilaque.mangaque.data.model.ChapterFrame
import com.vanilaque.mangaque.data.model.Manga

@Database(
    entities = [Chapter::class, ChapterFrame::class, Manga::class],
    version = 1,
)
@TypeConverters(DatabaseConverter::class)
abstract class MangaQueDatabase : RoomDatabase() {
    abstract fun chapterDao(): ChapterDao
    abstract fun chapterFrameDao(): ChapterFrameDao
    abstract fun mangaDao(): MangaDao
}