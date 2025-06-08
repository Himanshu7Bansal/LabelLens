package dev.h7b.labelLens.ui.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.graphics.Typeface
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import dev.h7b.labelLens.DetectedObjectBox

@Composable
fun Image(
    modifier: Modifier = Modifier,
    uri: Uri,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.FillWidth
) {
    val context = LocalContext.current
    var bitmap by remember(uri) { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(uri) {
        val inputStream = context.contentResolver.openInputStream(uri)
        bitmap = inputStream?.use {
            BitmapFactory.decodeStream(it)
        }
    }
    bitmap?.let {
        Image(
            modifier = modifier.fillMaxWidth(),
            bitmap = it.asImageBitmap(),
            contentDescription = contentDescription,
            contentScale = contentScale
        )
    } ?: Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(25.dp),
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 3.dp
        )
    }
}

@Composable
fun ImageWithLabel(
    modifier: Modifier = Modifier,
    bitmap: Bitmap?,
    detectedObjectBoxes: SnapshotStateList<DetectedObjectBox>
) {
    bitmap?.let { mBitmap ->
        Box(modifier = modifier) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                bitmap = mBitmap.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .drawBehind {
                        val scale = size.width / bitmap.width
                        detectedObjectBoxes.forEach { detectedObjectBox ->
                            val color = when (detectedObjectBox.confidence) {
                                in 0.85..1.0 -> Color.Green
                                in 0.7..<0.85 -> Color.Yellow
                                in 0.5..<0.7 -> Color.Magenta
                                else -> Color.Red
                            }
                            drawRect(
                                color = color,
                                topLeft = Offset(
                                    detectedObjectBox.boundingBox.left * scale,
                                    detectedObjectBox.boundingBox.top * scale
                                ),
                                size = Size(
                                    detectedObjectBox.boundingBox.width() * scale,
                                    detectedObjectBox.boundingBox.height() * scale
                                ),
                                style = Stroke(width = 4F)
                            )
                            drawContext.canvas.nativeCanvas.drawText(
                                "${detectedObjectBox.label} ${detectedObjectBox.confidence}",
                                detectedObjectBox.boundingBox.left * scale,
                                (detectedObjectBox.boundingBox.top - 10F) * scale,
                                Paint().apply {
                                    this.color = color.toArgb()
                                    textSize = 40F
                                    typeface = Typeface.DEFAULT_BOLD
                                }
                            )
                        }
                    }
            )
        }
    } ?: Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(25.dp),
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 3.dp
        )
    }
}
