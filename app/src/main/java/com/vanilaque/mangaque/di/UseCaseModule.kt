package com.vanilaque.mangaque.di

import com.vanilaque.mangaque.service.MangaLocalStoreService
import com.vanilaque.mangaque.usecase.MangaUseCase
import com.vanilaque.mangaque.service.PrefManager
import com.vanilaque.mangaque.usecase.ChapterUseCase
import com.vanilaque.mangareader.data.repository.ChapterFrameRepository
import com.vanilaque.mangareader.data.repository.ChapterRepository
import com.vanilaque.mangareader.data.repository.MangaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideMangaUseCase(prefManager: PrefManager, mangaRepository: MangaRepository, mangaLocalStoreService: MangaLocalStoreService): MangaUseCase {
        return MangaUseCase(mangaRepository, prefManager, mangaLocalStoreService)
    }

    @Provides
    fun provideChapterUseCase(prefManager: PrefManager, chapterRepository: ChapterRepository, chapterFrameRepository: ChapterFrameRepository): ChapterUseCase{
        return ChapterUseCase(chapterRepository, chapterFrameRepository, prefManager)
    }

//    @Provides
//    fun provideMangaLocalStoreService(@ApplicationContext context: Context): MangaLocalStoreService {
//        return MangaLocalStoreService(context)
//    }
}