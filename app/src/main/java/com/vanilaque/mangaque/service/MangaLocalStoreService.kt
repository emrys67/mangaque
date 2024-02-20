package com.vanilaque.mangaque.service

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.widget.Toast
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.vanilaque.mangaque.data.model.ChapterWithFrames
import com.vanilaque.mangaque.data.model.MangaWithChapters
import com.vanilaque.mangaque.data.repository.MangaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class MangaLocalStoreService @Inject constructor(
    private val context: Context,
    private val mangaRepository: MangaRepository
) {

    suspend fun saveManga(mangaWithChapters: MangaWithChapters) {
        try {
            val mangaDirectory = File(context.filesDir, "manga/${mangaWithChapters.manga.id}")
            if (!mangaDirectory.exists()) {
                mangaDirectory.mkdirs()
            }
            mangaWithChapters.manga.thumb.let {
                saveImage(it, "cover", mangaDirectory)
            }

            mangaWithChapters.chapters.forEach { chapter ->
                val chapterDirectory = File(mangaDirectory, chapter.chapter.id)
                chapter.frames.forEach { frame ->
                    saveImage(frame.link, frame.index.toString(), chapterDirectory)
                }
            }
            mangaWithChapters.let {
                mangaRepository.update(
                    it.manga.copy(
                        downloaded = true,
                        chaptersDownloaded = it.chapters.size
                    )
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Error while downloading images", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun loadChapterImagesFromFile(
        chapterWithFrames: ChapterWithFrames,
    ): List<Bitmap> {
        val bitmaps = mutableListOf<Bitmap>()
        val mangaId = chapterWithFrames.chapter.mangaId
        chapterWithFrames.let { chapter ->
            val chapterId = chapter.chapter.id
            chapter.frames.forEach {
                val path = "manga/$mangaId/$chapterId/img_${it.index}.png"
                val bitmap = loadBitmapFromStorage(path)
                bitmaps.add(bitmap)
            }
        }
        return bitmaps
    }

    fun loadMangaCoverFromFile(mangaId: String): Bitmap {
        val path = "manga/$mangaId/img_cover.png"
        return loadBitmapFromStorage(path)
    }

    suspend fun downloadMangaCoverFromServer(url: String): Bitmap {
        val loader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(url)
            .allowHardware(false)
            .build()

        val result = (loader.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }

    private suspend fun saveImage(url: String, fileName: String, dir: File) {
            Log.e("", "saveImage(url: String, fileName: String, dir: File)")
            var tryNumber = 0
            if (!dir.exists()) {
                dir.mkdirs()
            }
            val file = File(dir, "img_$fileName.png") // Assuming the images are jpg
            val loader = ImageLoader(context)
            val request = ImageRequest.Builder(context)
                .data(url)
                .allowHardware(false) // Disable hardware bitmaps.
                .build()
            var result =
                loader.execute(request)  //(loader.execute(request) as SuccessResult).drawable
            while (result !is SuccessResult || tryNumber < 3) {
                tryNumber++
                result = loader.execute(request)
                if (result !is SuccessResult && tryNumber == 3) {
                    Log.e("exc", "Image has not been downloaded $url")
                    return
                }

            }
            val bitmap = ((result as SuccessResult).drawable as BitmapDrawable).bitmap

            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, outputStream)
            outputStream.flush()
            outputStream.close()
    }

    private fun loadBitmapFromStorage(imagePath: String): Bitmap {
        val file =
            File(context.filesDir, imagePath)
        if (file.exists()) {
            return BitmapFactory.decodeFile(file.absolutePath)
        } else {
            Log.e("", "file doesnt exist")
            throw Exception()
        }
    }
}