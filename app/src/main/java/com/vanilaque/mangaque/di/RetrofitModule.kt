package com.vanilaque.mangaque.di

import androidx.paging.ExperimentalPagingApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.vanilaque.mangaque.api.MangaVerseApi
import com.vanilaque.mangaque.data.repository.local.LocalChapterRepository
import com.vanilaque.mangaque.data.repository.local.LocalMangaRepository
import com.vanilaque.mangaque.util.BASE_URL
import com.vanilaque.mangareader.data.repository.ChapterRepository
import com.vanilaque.mangareader.data.repository.MangaRepository
import com.vanilaque.mangareader.data.repository.impl.ChapterRepositoryImpl
import com.vanilaque.mangareader.data.repository.impl.MangaRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@ExperimentalPagingApi
@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    val logging = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.HEADERS)
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    val json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideMangaVerseApi(retrofit: Retrofit): MangaVerseApi {
        return retrofit.create(MangaVerseApi::class.java)
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
}