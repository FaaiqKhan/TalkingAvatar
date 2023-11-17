package com.practice.talkingavatar.utils

import android.content.Context
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

fun Context.createImageFile(): File {
    val imageFileName = "presenter_image"
    val presenterImagePath = Paths.get(applicationContext.dataDir.path + "/files/images")
    if (!Files.isDirectory(presenterImagePath)) Files.createDirectory(presenterImagePath)
    return File.createTempFile(imageFileName, ".jpg", presenterImagePath.toFile())
}

fun Context.createAnimationFile(name: String): File {
    val presenterAnimationPath = Paths.get(applicationContext.dataDir.path + "/files/animations")
    if (!Files.isDirectory(presenterAnimationPath)) Files.createDirectory(presenterAnimationPath)
    return File.createTempFile(name, ".mp4", presenterAnimationPath.toFile())
}