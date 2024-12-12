package com.example.vk_homework_2

import com.android.identity.util.UUID


data class GifResponse(
    val data: GifData
)

data class GifData(
    val images: GifImages
)

data class GifImages(
    val original: GifOriginal
)

data class GifOriginal(
    val url: String
)

sealed class GifState {
    data class Loaded(val url: String) : GifState()
    data class Failed(val id: String = UUID.randomUUID().toString()) : GifState()
}

interface RequestController {
    suspend fun fetchGifFromApi(): String
}


