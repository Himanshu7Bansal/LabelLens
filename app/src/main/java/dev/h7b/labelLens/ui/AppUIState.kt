package dev.h7b.labelLens.ui

import android.net.Uri


sealed interface AppUIState {

    data object PickImage : AppUIState
    data class ShowAnalysedImage(val uri: Uri) : AppUIState
}
