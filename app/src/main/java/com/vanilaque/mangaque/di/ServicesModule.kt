package com.vanilaque.mangaque.di

import android.content.Context
import com.vanilaque.mangaque.service.MangaLocalStoreService
import com.vanilaque.mangaque.service.PrefManager
import com.vanilaque.mangaque.data.repository.MangaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ServicesModule {

    @Provides
    fun providePrefManager(@ApplicationContext context: Context): PrefManager {
        return PrefManager(context)
    }

    @Provides
    fun provideMangaLocalStoreService(@ApplicationContext context: Context, mangaRepository: MangaRepository): MangaLocalStoreService {
        return MangaLocalStoreService(context, mangaRepository)
    }
}