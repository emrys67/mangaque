package com.vanilaque.mangaque.di

import android.content.Context
import androidx.room.Room
import com.vanilaque.mangaque.data.db.MangaQueDatabase
import com.vanilaque.mangaque.util.MANGA_DATABASE
import com.vanilaque.mangareader.data.repository.local.LocalChapterFrameRepository
import com.vanilaque.mangareader.data.repository.local.LocalChapterRepository
import com.vanilaque.mangareader.data.repository.local.LocalMangaRepository
import com.vanilaque.mangareader.data.repository.local.impl.LocalChapterFrameRepositoryImpl
import com.vanilaque.mangareader.data.repository.local.impl.LocalChapterRepositoryImpl
import com.vanilaque.mangareader.data.repository.local.impl.LocalMangaRepositoryImpl
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
    fun provideChapterRepository(
        database: MangaQueDatabase
    ): LocalChapterRepository {
        return LocalChapterRepositoryImpl(
            database = database
        )
    }

    @Provides
    @Singleton
    fun provideChapterFrameRepository(
        database: MangaQueDatabase
    ): LocalChapterFrameRepository {
        return LocalChapterFrameRepositoryImpl(
            database = database
        )
    }

    @Provides
    @Singleton
    fun provideMangaRepository(
        database: MangaQueDatabase
    ): LocalMangaRepository {
        return LocalMangaRepositoryImpl(
            database = database
        )
    }
}