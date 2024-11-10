package com.dejvidleka.moviehub_jetpackcompose.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight

@Composable
fun MetallicTitle() {
    val isDarkTheme = isSystemInDarkTheme()
    val gradientColors = if (isDarkTheme) {
        listOf(
            Color(0xFFFFFFFF),
            Color(0xFFF0F0F0),
            Color(0xFFD8D8D8),
            Color(0xFFC0C0C0),
            Color(0xFFD8D8D8),
            Color(0xFFF0F0F0),
            Color(0xFFFFFFFF)
        )
    } else {
        listOf(
            Color(0xFF787878),
            Color(0xFF666666),
            Color(0xFF545454),
            Color(0xFF484848),
            Color(0xFF545454),
            Color(0xFF666666),
            Color(0xFF888888)
        )
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "KinoHub",
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.ExtraBold,
                shadow = Shadow(
                    color = Color(0xFF808080),
                    offset = Offset(2f, 2f),
                    blurRadius = 3f
                )
            ),
            modifier = Modifier
                .graphicsLayer(alpha = 0.99f)
                .drawWithCache {
                    val brush = Brush.linearGradient(
                        colors = gradientColors,
                        start = Offset(0f, 0f),
                        end = Offset(size.width, size.height)
                    )
                    onDrawWithContent {
                        drawContent()
                        drawRect(brush, blendMode = BlendMode.SrcAtop)
                    }
                }
        )
    }
}