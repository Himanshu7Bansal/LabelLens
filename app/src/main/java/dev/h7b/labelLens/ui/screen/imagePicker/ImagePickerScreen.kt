package dev.h7b.labelLens.ui.screen.imagePicker

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.ImageOnly
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dev.h7b.labelLens.R
import dev.h7b.labelLens.ui.components.ButtonPrimary

@Composable
fun BoxScope.ImagePickerScreen(
    modifier: Modifier = Modifier,
    onImageSelected: (uri: Uri?) -> Unit
) {
    val pickMedia = rememberLauncherForActivityResult(PickVisualMedia()) { uri ->
        onImageSelected(uri)
    }
    ButtonPrimary(
        modifier = modifier.align(Alignment.Center),
        text = stringResource(R.string.open_photos),
        onClick = { pickMedia.launch(PickVisualMediaRequest(ImageOnly)) }
    )
}
