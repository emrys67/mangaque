package com.vanilaque.mangaque.usecase

import android.util.Log
import com.vanilaque.mangaque.service.PrefManager
import com.vanilaque.mangareader.data.repository.ChapterFrameRepository
import com.vanilaque.mangareader.data.repository.ChapterRepository
import timber.log.Timber
import javax.inject.Inject

class ChapterUseCase @Inject constructor(
    private val chapterRepository: ChapterRepository,
    private val chapterFrameRepository: ChapterFrameRepository,
    private val prefManager: PrefManager
) {
    suspend fun syncChapters(mangaId: String){
        val chapters = chapterRepository.fetchFromTheServer(mangaId)
        for ((index, chapter) in chapters.withIndex()){
            chapter.index = index
        }
        Log.e("synchChapters","syncChapters(mangaId: String) ${chapters.toString()}")
        chapterRepository.insert(chapters)
        chapters.forEach{
            val chapterFrames = chapterFrameRepository.fetchFromTheServer(it.id)
            chapterFrameRepository.insertAll(chapterFrames)
        }
    }
}