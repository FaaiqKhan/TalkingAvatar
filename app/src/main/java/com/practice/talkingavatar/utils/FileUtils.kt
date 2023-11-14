package com.practice.talkingavatar.utils

import android.content.Context
import android.os.Environment
import com.practice.talkingavatar.model.data.PresenterModel
import java.io.*
import java.net.URL
import java.nio.file.Path
import java.util.UUID

object FileUtils {

    private const val imagePath: String = "/files/images"
    private const val animationPath: String = "/files/animations"
//
//    fun createImageFolderIfNotExist() {
//        val imageFile = File(imagePath)
//        if (!imageFile.exists()) imageFile.mkdir()
//    }
//
//    fun createLoggerFolderIfNotExit(context: Context) {
//        val loggerFolderFile =
//            File(Environment.DIRECTORY_DOCUMENTS + "/${Utils.getApplicationName(context)}")
//        if (!loggerFolderFile.exists()) loggerFolderFile.mkdir()
//    }

    fun downloadAnimationFromUrl(
        context: Context,
        url: String,
        fileName: String,
        format: String? = ".mp4",
    ): String? {
        val animationFile = File(context.dataDir.path + animationPath)
        if (!animationFile.exists())
            animationFile.mkdir()

        try {
            val animationFileName = "$fileName$format"
            val downloadAnimationFile = File(animationFile, animationFileName)

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