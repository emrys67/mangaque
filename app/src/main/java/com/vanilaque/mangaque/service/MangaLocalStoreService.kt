package com.vanilaque.mangaque.service

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.widget.Toast
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.vanilaque.mangaque.data.model.MangaWithChapters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class MangaLocalStoreService @Inject constructor(private val context: Context) {

    suspend fun saveWebtoon(mangaWithChapters: MangaWithChapters) {
        withContext(Dispatchers.IO) {
            try {
                val webtoonDirectory = File(context.filesDir, "webtoons/${mangaWithChapters.manga.id}")
                if (!webtoonDirectory.exists()) {
                    webtoonDirectory.mkdirs()
                }
                mangaWithChapters.manga.thumb.let {
                    saveImage(it, "cover", webtoonDirectory)
                }

                mangaWithChapters.chapters.forEach { chapter ->
                    val chapterDirectory = File(webtoonDirectory, chapter.chapter.id)
                    chapter.frames.forEach { frame ->
                        saveImage(frame.link, frame.index.toString(), chapterDirectory)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Error while downloading images", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    fun loadChapterImagesFromFile(
        mangaWithChapters: MangaWithChapters,
        chapterNum: Int
    ): List<Bitmap> {
        val bitmaps = mutableListOf<Bitmap>()
        val mangaId = mangaWithChapters.manga.id
        mangaWithChapters.chapters[chapterNum].let { chapter ->
            val chapterId = chapter.chapter.id
            chapter.frames.forEach {
                val path = "webtoons/$mangaId/$chapterId/img_${it.index}.png"
                val bitmap = loadBitmapFromStorage(path)
                bitmaps.add(bitmap)
            }
        }
        return bitmaps
    }

//    fun loadWebtoonCoverFromFile(webtoonSlug: String): Bitmap {
//        val path = "webtoons/$webtoonSlug/img_cover.jpg"
//        return loadBitmapFromStorage(path)
//    }
//
    suspend fun downloadWebtoonCoverFromServer(url: String): Bitmap = withContext(Dispatchers.IO) {
    val loader = ImageLoader(context)
    val request = ImageRequest.Builder(context)
        .data(url)
        .allowHardware(false) // Disable hardware bitmaps.
        .build()

    val result = (loader.execute(request) as SuccessResult).drawable
    return@withContext (result as BitmapDrawable).bitmap
    }

    private suspend fun saveImage(url: String, fileName: String, dir: File) {
        Log.e("", "saveImage(url: String, fileName: String, dir: File)")
        if (!dir.exists()) {
            dir.mkdirs()
        }
        val file = File(dir, "img_$fileName.png") // Assuming the images are jpg

//        val bitmap = Coil.with(context)
//            .asBitmap()
//            .load(url)
//            .submit()
//            .get()

        val loader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(url)
            .allowHardware(false) // Disable hardware bitmaps.
            .build()

        val result = (loader.execute(request) as SuccessResult).drawable
        val bitmap = (result as BitmapDrawable).bitmap

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