package com.vanilaque.mangaque.di

import android.content.Context
import androidx.room.Room
import com.vanilaque.mangaque.api.MangaVerseApi
import com.vanilaque.mangaque.data.db.MangaQueDatabase
import com.vanilaque.mangaque.data.repository.ChapterFrameRepository
import com.vanilaque.mangaque.data.repository.ChapterRepository
import com.vanilaque.mangaque.data.repository.MangaRepository
import com.vanilaque.mangaque.data.repository.impl.ChapterFrameRepositoryImpl
import com.vanilaque.mangaque.data.repository.impl.ChapterRepositoryImpl
import com.vanilaque.mangaque.data.repository.impl.MangaRepositoryImpl
import com.vanilaque.mangaque.data.repository.local.LocalChapterFrameRepository
import com.vanilaque.mangaque.data.repository.local.LocalChapterRepository
import com.vanilaque.mangaque.data.repository.local.LocalMangaRepository
import com.vanilaque.mangaque.data.repository.local.impl.LocalChapterFrameRepositoryImpl
import com.vanilaque.mangaque.data.repository.local.impl.LocalChapterRepositoryImpl
import com.vanilaque.mangaque.data.repository.local.impl.LocalMangaRepositoryImpl
import com.vanilaque.mangaque.util.MANGA_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): MangaQueDatabase {
        return Room.databaseBuilder(
            context,
            MangaQueDatabase::class.java,
            MANGA_DATABASE
        ).build()
    }

    @Provides
    @Singleton
    fun provideLocalChapterRepository(
        database: MangaQueDatabase
    ): LocalChapterRepository {
        return LocalChapterRepositoryImpl(
            database = database
        )
    }

    @Provides
    @Singleton
    fun provideLocalChapterFrameRepository(
        database: MangaQueDatabase
    ): LocalChapterFrameRepository {
        return LocalChapterFrameRepositoryImpl(
            database = database
        )
    }

    @Provides
    @Singleton
    fun provideLocalMangaRepository(
        database: MangaQueDatabase
    ): LocalMangaRepository {
        return LocalMangaRepositoryImpl(
            database = database
        )
    }

    @Provides
    @Singleton
    fun provideMangaRepository(
        api: MangaVerseApi,
        localRepo: LocalMangaRepository
    ): MangaRepository {
        return MangaRepositoryImpl(api, localRepo)
    }

    @Provides
    @Singleton
    fun provideChaptersRepository(
        api: MangaVerseApi,
        localRepo: LocalChapterRepository
    ): ChapterRepository {
        return ChapterRepositoryImpl(localRepo, api)
    }

    @Provides
    @Singleton
    fun provideChapterFrameRepository(
        api: MangaVerseApi,
        localRepo: LocalChapterFrameRepository
    ): ChapterFrameRepository {
        return ChapterFrameRepositoryImpl(localRepo, api)
    }
}