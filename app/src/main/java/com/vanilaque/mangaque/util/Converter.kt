package com.vanilaque.mangaque.util

import android.icu.text.SimpleDateFormat
import com.vanilaque.mangaque.api.Chapter
import com.vanilaque.mangaque.api.ChapterFrame
import com.vanilaque.mangaque.api.Manga
import java.util.Date
import java.util.Locale

fun Manga.toDbModel() = com.vanilaque.mangaque.data.model.Manga(
    id = this.id,
    title = this.title,
    subTitle = this.subTitle,
    status = this.status,
    thumb = this.thumb,
    summary = this.summary,
    authors = this.authors,
    genres = this.genres,
    nsfw = this.nsfw,
    type = this.type,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
)

fun Chapter.toDbModel() = com.vanilaque.mangaque.data.model.Chapter(
    id = this.id,
    mangaId = this.manga,
    index = 0, //can produce exception
    title = this.title,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

fun ChapterFrame.toDbModel() = com.vanilaque.mangaque.data.model.ChapterFrame(
    id = this.id,
    chapterId = this.chapter,
    mangaId = this.manga,
    index = this.index,
    link = this.link
)

fun millisToDateString(millis: Long): String {
    val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    return sdf.format(Date(millis))
}