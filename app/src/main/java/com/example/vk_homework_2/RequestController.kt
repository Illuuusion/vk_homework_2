package com.example.vk_homework_2


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


interface RequestController {
    suspend fun fetchGifFromApi(): String
}


