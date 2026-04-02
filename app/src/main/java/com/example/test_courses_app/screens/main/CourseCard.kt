package com.example.test_courses_app.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.test_courses_app.domain.Course

@Composable
fun CourseCard(
    course: Course,
    onFavoriteToggle: (Boolean) -> Unit
) {
    var isFavorite by remember { mutableStateOf(course.hasLike) }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = course.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { 
                    isFavorite = !isFavorite
                    onFavoriteToggle(isFavorite)
                }) {
                    Text(text = if (isFavorite) "💚" else "🤍")
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = course.text,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(course.price, style = MaterialTheme.typography.titleLarge)
                Text("⭐ ${course.rate}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}