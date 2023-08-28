package com.vanilaque.mangaque.di

import com.vanilaque.mangaque.MangaUseCase
import com.vanilaque.mangaque.api.MangaVerseApi
import com.vanilaque.mangaque.service.PrefManager
import com.vanilaque.mangareader.data.repository.MangaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideMangaUseCase(prefManager: PrefManager, mangaRepository: MangaRepository): MangaUseCase {
        return MangaUseCase(mangaRepository, prefManager)
    }

//    @Provides
//    fun provideMangaLocalStoreService(@ApplicationContext context: Context): MangaLocalStoreService {
//        return MangaLocalStoreService(context)
//    }
}