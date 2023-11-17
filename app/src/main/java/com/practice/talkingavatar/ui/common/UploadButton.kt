package com.practice.talkingavatar.ui.common

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.practice.talkingavatar.R
import com.practice.talkingavatar.ui.theme.TalkingAvatarTheme
import com.practice.talkingavatar.utils.createImageFile

@Composable
fun UploadButton(modifier: Modifier = Modifier, onClick: (imageUri: Uri) -> Unit) {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        it?.let { image ->
            val imageFile = context.createImageFile()
            context.contentResolver.openInputStream(image)?.use { input ->
                imageFile.outputStream().use { output -> input.copyTo(output) }
            }
            onClick(imageFile.toUri())
        }
    }

    Card(
        modifier = modifier.clickable {
            launcher.launch(
                PickVisualMediaRequest(
                    mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                )
            )
        },
        shape = RoundedCornerShape(percent = 100),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onBackground
        )
    ) {
        Icon(
            painterResource(id = R.drawable.ic_upload),
            contentDescription = stringResource(R.string.upload),
            modifier = Modifier
                .size(64.dp)
                .padding(all = 18.dp)
        )
    }
}

@Preview
@Composable
private fun PreviewUploadButton() {
    TalkingAvatarTheme {
        UploadButton(onClick = {})
    }
}