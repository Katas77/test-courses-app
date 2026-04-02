package com.example.test_courses_app.presentation.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.test_courses_app.presentation.main.CourseCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoriteScreen(viewModel: FavoriteViewModel = koinViewModel()) {
    val favorites by viewModel.favorites.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("❤️ Избранное", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        
        if (favorites.isEmpty()) {
            Text("Здесь будут ваши любимые курсы")
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(favorites) { course ->
                    CourseCard(course = course, onFavoriteToggle = { viewModel.removeFromFavorites(it) })
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}