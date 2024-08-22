package com.dejvidleka.moviehub_jetpackcompose.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.dejvidleka.data.di.Result
import com.dejvidleka.data.remote.models.MovieResult
import com.dejvidleka.moviehub_jetpackcompose.ui.viewmodels.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(contentWindowInsets = WindowInsets(0.dp), topBar = {
        CenterAlignedTopAppBar(title = {
            Text(text = "KinoHUB", style = MaterialTheme.typography.titleLarge)
        }, navigationIcon = {
            IconButton(onClick = { }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "")
            }
        }

        )
    }) { it ->
        LazyColumn(modifier = Modifier.padding(it)) {
            when (uiState) {
                is Result.Success -> {
                    items((uiState as Result.Success<List<MovieResult>>).data) { movie ->
                        MovieCard(movie)
                        Log.d("movie here", "$movie")
                    }
                }

                is Result.Loading -> {
                    Log.d("movie here", "Loading")

                }
                is Result.Error -> {
                    Log.d("movie here", "error")
                }
            }
        }

    }
}

@Composable
fun MovieCard(movie: MovieResult) {
    ElevatedCard(
        onClick = { }, modifier = Modifier
            .width(100.dp)
            .height(120.dp)
    ) {
        Column {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500/${movie.poster_path}",
                contentDescription = "Movie Poster",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )
            Text(text = movie.title ?: "", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
