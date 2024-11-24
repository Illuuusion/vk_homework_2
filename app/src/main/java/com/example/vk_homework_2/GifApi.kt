package com.example.vk_homework_2

import retrofit2.http.GET

interface GifApi {
    @GET("random?api_key=tsg2RYd9L9pZG8hN8uzVRUnTNJ88F7Dm")
    suspend fun getRandomGif(): GifResponse
}