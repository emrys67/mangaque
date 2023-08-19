package com.vanilaque.mangaque.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MangaVerseApi {
    @GET("/manga/fetch")
    suspend fun fetchManga(
        @Header("X-RapidAPI-Key") key: String,
        @Header("X-RapidAPI-Host") host: String,
        @Query("page") page: String,
        @Query("genres") genres: String
    ): Response<MultipleMangaResponse>

    @GET("/manga/latest")
    suspend fun fetchLatest(
        @Header("X-RapidAPI-Key") key: String,
        @Header("X-RapidAPI-Host") host: String,
        @Query("page") page: String,
        @Query("genres") genres: String
    ): Response<MultipleMangaResponse>

    @GET("/manga/search")
    suspend fun searchManga(
        @Header("X-RapidAPI-Key") key: String,
        @Header("X-RapidAPI-Host") host: String,
        @Query("text") name: String,
    ): Response<MultipleMangaResponse>

    @GET("/manga")
    suspend fun getManga(
        @Header("X-RapidAPI-Key") key: String,
        @Header("X-RapidAPI-Host") host: String,
        @Query("id") id: String
    ): Response<MangaResponse>

    @GET("/manga/chapter")
    suspend fun fetchChapters(
        @Header("X-RapidAPI-Key") key: String,
        @Header("X-RapidAPI-Host") host: String,
        @Query("id") id: String,
    ): Response<ChaptersResponse>

    @GET("/manga/image")
    suspend fun fetchImages(
        @Header("X-RapidAPI-Key") key: String,
        @Header("X-RapidAPI-Host") host: String,
        @Query("id") id: String,
    ): Response<ChapterFramesResponse>
}