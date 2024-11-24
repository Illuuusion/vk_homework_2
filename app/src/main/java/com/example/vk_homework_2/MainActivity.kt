package com.example.vk_homework_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text


const val API = "https://api.giphy.com/v1/gifs/"


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                GiphyApp()
            }
        }
    }
}


@Composable
fun GiphyApp() {
    var images by remember { mutableStateOf<List<String>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    fun fetchRandomGif() {
        scope.launch {
            isLoading = true
            errorMessage = null
            try {
                val gifUrl = RetrofitController(API).fetchGifFromApi()
                images = images + gifUrl
            } catch (e: Exception) {
                errorMessage = "Failed to load GIF"
            } finally {
                isLoading = false
            }
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Button(
            onClick = { fetchRandomGif() },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Get Random Gif")
        }

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            errorMessage?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(16.dp))
            }

            LazyColumn(
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(images) { gifUrl ->
                    val painter = rememberAsyncImagePainter(gifUrl)
                    Image(
                        painter = painter,
                        contentDescription = "Random GIF",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                    )
                }
            }
        }
    }
}