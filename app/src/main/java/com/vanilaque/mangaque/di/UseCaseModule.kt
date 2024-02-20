package com.vanilaque.mangaque.di

import com.vanilaque.mangaque.data.repository.ChapterFrameRepository
import com.vanilaque.mangaque.data.repository.ChapterRepository
import com.vanilaque.mangaque.data.repository.MangaRepository
import com.vanilaque.mangaque.service.PrefManager
import com.vanilaque.mangaque.usecase.ChapterUseCase
import com.vanilaque.mangaque.usecase.MangaUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideMangaUseCase(
        prefManager: PrefManager,
        mangaRepository: MangaRepository
    ): MangaUseCase {
        return MangaUseCase(mangaRepository, prefManager)
    }

    @Provides
    fun provideChapterUseCase(
        chapterRepository: ChapterRepository,
        chapterFrameRepository: ChapterFrameRepository
    ): ChapterUseCase {
        return ChapterUseCase(chapterRepository, chapterFrameRepository)
    }
}