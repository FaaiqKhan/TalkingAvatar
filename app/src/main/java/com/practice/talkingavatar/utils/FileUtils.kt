package com.practice.talkingavatar.utils

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import com.practice.talkingavatar.model.data.PresenterModel
import java.io.*
import java.net.URL
import java.nio.file.Path
import java.nio.file.Paths
import java.util.Objects
import java.util.UUID

object FileUtils {

    fun getFileUri(context: Context): Uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        context.packageName + ".provider",
        context.createImageFile(),
    )

    fun getFileFromUri(uri: Uri, context: Context): File {
        return Paths.get(context.dataDir.path + uri.path).toFile()
    }

    fun downloadAnimationFromUrl(
        context: Context,
        url: String,
        fileName: String,
    ): String? {
        try {
            val animationFile = context.createAnimationFile(fileName)
            val downloadAnimationFile = File(animationFile, animationFile.name)

            val inputStream = URL(url).openStream()
            val outputStream = FileOutputStream(downloadAnimationFile)

            val buf = ByteArray(DEFAULT_BUFFER_SIZE)
            var len: Int

            while (inputStream.read(buf).also { len = it } != -1) {
                outputStream.write(buf, 0, len)
            }

            outputStream.flush()
            inputStream.close()
            outputStream.close()

            return downloadAnimationFile.path

        } catch (e: Exception) {
            throw e
        }
    }

}