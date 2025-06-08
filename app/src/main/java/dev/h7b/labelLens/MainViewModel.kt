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
import com.google.mlkit.common.model.LocalModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.custom.CustomObjectDetectorOptions

class MainViewModel : ViewModel() {

    var bitmap by mutableStateOf<Bitmap?>(null)
    val detectedObjectBoxes = mutableStateListOf<DetectedObjectBox>()
    private val localModel by lazy {
        LocalModel.Builder()
            .setAssetFilePath("models/mobile_object_labeler_V1.tflite")
            .build()
    }
    private val customObjectDetectorOptions by lazy {
        CustomObjectDetectorOptions.Builder(localModel)
            .setDetectorMode(CustomObjectDetectorOptions.SINGLE_IMAGE_MODE)
            .enableMultipleObjects()
            .enableClassification()
            //.setClassificationConfidenceThreshold(0.5f)
            .build()
    }
    private val objectDetector by lazy {
        ObjectDetection.getClient(customObjectDetectorOptions)
    }

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
