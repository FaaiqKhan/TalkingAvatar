package com.practice.talkingavatar.utils

import android.content.Context
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.UUID

fun Context.createImageFile(): File {
    val imageFileName = "presenter_image"
    val presenterImagePath = Paths.get(applicationContext.dataDir.path + "/files/images")
    if (!Files.isDirectory(presenterImagePath)) Files.createDirectory(presenterImagePath)
    return File.createTempFile(
        imageFileName, /* prefix */
        ".jpg", /* suffix */
        presenterImagePath.toFile() /* directory */
    )
}

fun Context.createAnimationFile(): File {
    val presenterAnimationPath = Paths.get(applicationContext.dataDir.path + "/files/animations")
    if (!Files.isDirectory(presenterAnimationPath)) Files.createDirectory(presenterAnimationPath)

    return File.createTempFile(
        UUID.randomUUID().toString(),
        ".mp4",
        presenterAnimationPath.toFile()
    )
}