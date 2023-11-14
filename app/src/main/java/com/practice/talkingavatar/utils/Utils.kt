package com.practice.talkingavatar.utils

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import arrow.core.Either
import com.practice.talkingavatar.model.data.PresenterErrorModel
import com.practice.talkingavatar.model.data.PresenterModel
import com.practice.talkingavatar.model.repository.DidServiceRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.json.JSONObject
import retrofit2.HttpException
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import java.util.Objects

enum class Screens {
    HOME,
    SETTING
}

interface Poller {

    fun poll(delay: Long): Flow<Either<PresenterErrorModel, PresenterModel>>
    fun close()
}

class CoroutinePoller(
    private val presenterId: String,
    private val dispatcher: CoroutineDispatcher,
    private val didServiceRepository: DidServiceRepository,
) : Poller {

    @OptIn(DelicateCoroutinesApi::class)
    override fun poll(delay: Long): Flow<Either<PresenterErrorModel, PresenterModel>> =
        channelFlow {
            while (!isClosedForSend) {
                didServiceRepository.getPresenter(presenterId)
                    .fold(
                        { error -> send(Either.Left(error)) },
                        { presenter -> send(Either.Right(presenter)) },
                    )
                delay(delay)
            }
        }.flowOn(dispatcher)

    override fun close() = dispatcher.cancel()

}

object Utils {

    fun getFileFromUri(uri: Uri, context: Context): File {
        return Paths.get(context.dataDir.path + uri.path).toFile()
    }

    fun parseDIDServiceError(exception: Exception): PresenterErrorModel {
        return if (exception is HttpException && exception.response()?.errorBody() != null) {
            val errorBody = exception.response()!!.errorBody()!!.string()
            val jObjError = JSONObject(errorBody)
            PresenterErrorModel(
                kind = jObjError["kind"] as String,
                description = jObjError["description"] as String
            )
        } else {
            PresenterErrorModel(
                kind = "Unknown",
                description = "Something went wrong"
            )
        }
    }

    fun getFileUri(context: Context): Uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        context.packageName + ".provider",
        context.createImageFile(),
    )

    fun getApplicationLogo(context: Context): String =
        if (context.packageName != "com.practice.talkingavatar.techwardsai") {
            "file:///android_asset/techwards_ai_logo.png"
        } else {
            "file:///android_asset/mate_ai_logo.png"
        }

    fun getApplicationName(context: Context): String =
        if (context.packageName != "com.practice.talkingavatar.techwardsai") {
            "TechwardsAI"
        } else {
            "MateAI"
        }
}