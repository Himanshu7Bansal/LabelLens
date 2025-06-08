package dev.h7b.labelLens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Rect
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions

class MainViewModel : ViewModel() {

    var bitmap by mutableStateOf<Bitmap?>(null)
    val detectedObjectBoxes = mutableStateListOf<DetectedObjectBox>()
    private val options by lazy {
        ObjectDetectorOptions.Builder()
            .setDetectorMode(ObjectDetectorOptions.SINGLE_IMAGE_MODE)
            .enableClassification()
            .enableMultipleObjects()
            .build()
    }
    private val objectDetector by lazy { ObjectDetection.getClient(options) }

    fun detectObjectsFromImageUri(
        context: Context,
        uri: Uri
    ) {
        val image = InputImage.fromFilePath(context, uri)
        objectDetector.process(image)
            .addOnSuccessListener { detectedObjects ->
                bitmap = null
                val boxes = detectedObjects.map {
                    val label = it.labels.firstOrNull()
                    DetectedObjectBox(
                        label?.text ?: "Unknown",
                        label?.index ?: 0,
                        label?.confidence ?: 0F,
                        it.boundingBox
                    )
                }
                detectedObjectBoxes.clear()
                detectedObjectBoxes.addAll(boxes)
                bitmap = image.bitmapInternal
                Log.e("MainViewModel", "detected objects $detectedObjects")
                Log.e("MainViewModel", "$detectedObjectBoxes")
            }
            .addOnFailureListener {
                Log.e("MainViewModel", "Error detecting objects", it)
            }
    }
}

data class DetectedObjectBox(
    val label: String,
    val category: Int,
    val confidence: Float,
    val boundingBox: Rect
)
