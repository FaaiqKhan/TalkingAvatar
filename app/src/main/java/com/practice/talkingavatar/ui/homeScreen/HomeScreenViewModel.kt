package com.practice.talkingavatar.ui.homeScreen

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import arrow.core.Either
import com.practice.talkingavatar.R
import com.practice.talkingavatar.model.data.PresenterImage
import com.practice.talkingavatar.model.data.PresenterModel
import com.practice.talkingavatar.model.repository.DidServiceRepository
import com.practice.talkingavatar.model.repository.S3Repository
import com.practice.talkingavatar.ui.model.HomeScreenUiModel
import com.practice.talkingavatar.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val s3Repository: S3Repository,
    private val repository: DidServiceRepository,
    val exoPlayer: ExoPlayer,
) : ViewModel() {

    val idleAnimation = "asset:///generic_animation.mp4"

    private val _screenState = MutableStateFlow<HomeScreenUiState>(HomeScreenUiState.Idle)
    val screenState = _screenState.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        viewModelScope.launch {
            _screenState.emit(
                HomeScreenUiState.Failure(
                    message = exception.localizedMessage ?: "Something went wrong"
                )
            )
        }
    }

    init {
        exoPlayer.prepare()
        exoPlayer.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == Player.STATE_ENDED && exoPlayer.mediaMetadata.title != "idleAnimation")
                    viewModelScope.launch { _screenState.emit(HomeScreenUiState.Idle) }
            }

            override fun onPlayerError(error: PlaybackException) {
                viewModelScope.launch {
                    _screenState.emit(
                        HomeScreenUiState.Failure(
                            message = error.localizedMessage ?: "Something went wrong"
                        )
                    )
                }
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        exoPlayer.release()
    }

    fun createPresenter(
        imageUri: Uri,
        context: Context,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
    ) {

        viewModelScope.launch(dispatcher + exceptionHandler) {
            _screenState.emit(HomeScreenUiState.Loading)

            val imageFile = FileUtils.getFileFromUri(imageUri, context)

            val expirationTime: LocalDateTime = LocalDate.now()
                .plusDays(7)
                .atStartOfDay()

            val imagePreSignedUrl = s3Repository.generatePreSignedUrlAndUploadImage(
                imageFile,
                expirationTime.toInstant(ZoneOffset.UTC)
            )

            val presenterReference = repository.generatePresenter(imagePreSignedUrl).run {
                if (this.isLeft()) {
                    _screenState.emit(
                        HomeScreenUiState.Failure(
                            message = (this as Either.Left).value.description
                        )
                    )
                    return@launch
                }
                (this as Either.Right).value
            }

            var presenter: PresenterModel? = null

            CoroutinePoller(presenterReference.id, Dispatchers.IO, repository).apply {
                try {
                    poll(1000).collect {
                        if (it.isLeft()) {
                            _screenState.emit(
                                HomeScreenUiState.Failure(
                                    message = (it as Either.Left).value.description
                                )
                            )
                            cancel()
                        } else {
                            presenter = (it as Either.Right).value
                            if (presenter?.status == "done") cancel()
                        }
                    }
                } catch (e: Exception) {
                    _screenState.emit(
                        HomeScreenUiState.Failure(
                            message = context.getString(R.string.unable_to_fetch_presenter)
                        )
                    )
                } finally {
                    close()
                }
            }

            if (presenter == null) {
                Toast.makeText(
                    context,
                    context.getString(R.string.unable_to_create_presenter),
                    Toast.LENGTH_SHORT
                ).show()
                return@launch
            }

            val animationPath = FileUtils.downloadAnimationFromUrl(
                context = context,
                fileName = imageFile.nameWithoutExtension,
                url = presenter!!.resultUrl!!,
            ) ?: return@launch

            presenter = presenter?.copy(
                filePath = animationPath,
                image = PresenterImage(
                    id = imageFile.name,
                    url = imagePreSignedUrl,
                    expiry = expirationTime,
                    filePath = imageFile.path
                ),
            )

            _screenState.emit(HomeScreenUiState.Success(HomeScreenUiModel(animationPath)))
        }
    }
}

sealed class HomeScreenUiState {
    object Idle : HomeScreenUiState()
    object Loading : HomeScreenUiState()
    data class Failure(val message: String) : HomeScreenUiState()
    data class Success(val model: HomeScreenUiModel) : HomeScreenUiState()
}