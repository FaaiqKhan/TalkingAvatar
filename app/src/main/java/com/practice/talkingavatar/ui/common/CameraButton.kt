package com.practice.talkingavatar.ui.common

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.practice.talkingavatar.ui.theme.TalkingAvatarTheme
import com.practice.talkingavatar.utils.FileUtils

@Composable
fun CameraButton(modifier: Modifier = Modifier, onClick: (imageUri: Uri) -> Unit) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri>(Uri.EMPTY) }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it && imageUri != Uri.EMPTY) {
            onClick(imageUri)
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            imageUri = FileUtils.getFileUri(context = context)
            cameraLauncher.launch(imageUri)
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    Card(
        modifier = modifier.clickable {
            val permissionCheckResult = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            )
            if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                imageUri = FileUtils.getFileUri(context = context)
                cameraLauncher.launch(imageUri)
            } else {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
        },
        shape = RoundedCornerShape(percent = 100),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onBackground
        )
    ) {
        Icon(
            painterResource(id = com.practice.talkingavatar.R.drawable.ic_camera),
            contentDescription = "Camera",
            modifier = Modifier
                .size(64.dp)
                .padding(all = 18.dp)
        )
    }
}

@Preview
@Composable
private fun PreviewCameraButton() {
    TalkingAvatarTheme {
        CameraButton(onClick = {})
    }
}