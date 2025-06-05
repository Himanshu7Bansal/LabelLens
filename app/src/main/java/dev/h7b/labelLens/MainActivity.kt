package dev.h7b.labelLens

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dev.h7b.labelLens.ui.AppUIState
import dev.h7b.labelLens.ui.screen.imagePicker.ImagePickerScreen
import dev.h7b.labelLens.ui.screen.objectIdentifier.ObjectIdentifierScreen
import dev.h7b.labelLens.ui.theme.LabelLensTheme

class MainActivity : ComponentActivity() {

    private var appUIState: AppUIState by mutableStateOf(AppUIState.PickImage)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LabelLensTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        when (appUIState) {
                            AppUIState.PickImage -> {
                                ImagePickerScreen(onImageSelected = ::onImageSelected)
                            }

                            is AppUIState.ShowAnalysedImage -> {
                                ObjectIdentifierScreen(
                                    uri = (appUIState as AppUIState.ShowAnalysedImage).uri,
                                    onGoToMain = ::onGoToMain
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun onImageSelected(uri: Uri?) {
        if (uri != null) {
            appUIState = AppUIState.ShowAnalysedImage(uri)
        } else {
            Toast
                .makeText(this, getString(R.string.error_selecting_image), Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun onGoToMain() {
        appUIState = AppUIState.PickImage
    }
}
