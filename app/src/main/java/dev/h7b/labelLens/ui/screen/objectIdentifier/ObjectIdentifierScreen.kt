package dev.h7b.labelLens.ui.screen.objectIdentifier

import android.net.Uri
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.h7b.labelLens.R
import dev.h7b.labelLens.ui.components.ButtonPrimary
import dev.h7b.labelLens.ui.theme.HorizontalPadding
import dev.h7b.labelLens.ui.theme.VerticalPadding

@Composable
fun BoxScope.ObjectIdentifierScreen(
    uri: Uri,
    onGoToMain: () -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(horizontal = HorizontalPadding, vertical = VerticalPadding)
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = stringResource(R.string.original_image))
        Spacer(modifier = Modifier.height(12.dp))
        //Image
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = stringResource(R.string.object_detection_image))
        Spacer(modifier = Modifier.height(12.dp))
        //Image
    }
    ButtonPrimary(
        modifier = Modifier
            .padding(start = HorizontalPadding, end = HorizontalPadding, bottom = VerticalPadding)
            .fillMaxWidth()
            .align(Alignment.BottomCenter),
        text = stringResource(R.string.go_to_main),
        onClick = onGoToMain
    )
}
