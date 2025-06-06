package dev.jdgarita.nutrisport.manage_product.util

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import dev.gitlive.firebase.storage.File

actual class PhotoPicker {

    private var openPhotoPicker = mutableStateOf(false)

    @Composable
    actual fun InitializePhotoPicker(onImageSelect: (File?) -> Unit) {
        val openPhotoPickerState by remember { openPhotoPicker }
        val pickMedia = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
        ) { uri ->
            uri?.let {
                onImageSelect(File(uri))
            } ?: onImageSelect(null)
            openPhotoPicker.value = false
        }

        LaunchedEffect(openPhotoPickerState) {
            if (openPhotoPickerState) {
                pickMedia.launch(
                    PickVisualMediaRequest(
                        mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            }
        }
    }

    actual fun open() {
        openPhotoPicker.value = true
    }
}