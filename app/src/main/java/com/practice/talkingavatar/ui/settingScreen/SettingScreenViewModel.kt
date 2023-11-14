package com.practice.talkingavatar.ui.settingScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.practice.talkingavatar.model.data.DidCreditModel
import com.practice.talkingavatar.model.data.ElevenLabSubscriptionModel
import com.practice.talkingavatar.model.repository.DidServiceRepository
import com.practice.talkingavatar.model.repository.ElevenLabRepository
import com.practice.talkingavatar.ui.model.SettingScreenUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingScreenViewModel @Inject constructor(
    private val didRepository: DidServiceRepository,
    private val elevenLabRepository: ElevenLabRepository,
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<SettingScreenUiState>(SettingScreenUiState.Loading)
    val uiState: StateFlow<SettingScreenUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val didCredits = async { didRepository.getDidCredits() }
            val elevenLabCredits = async { elevenLabRepository.getSubscription() }
            val elevenLabVoices = async { elevenLabRepository.getVoices() }

            val didCreditResult = didCredits.await()
            val elevenLabCreditResult = elevenLabCredits.await()
            val elevenLabVoicesResult = elevenLabVoices.await()

            val didCredit = if (didCreditResult.isLeft()) {
                DidCreditModel(credits = listOf(), remaining = 0, total = 0)
            } else {
                (didCreditResult as Either.Right).value
            }

            val elevenLabCredit = if (elevenLabCreditResult.isLeft()) {
                ElevenLabSubscriptionModel(
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                )
            } else {
                (elevenLabCreditResult as Either.Right).value
            }

            val totalVoice = if (elevenLabVoicesResult.isLeft()) {
                0
            } else {
                (elevenLabVoicesResult as Either.Right).value
            }

            if (didCreditResult.isLeft() && elevenLabCreditResult.isLeft() && elevenLabVoicesResult.isLeft()) {
                _uiState.emit(SettingScreenUiState.Failure("Error while fetching credits"))
            } else {
                _uiState.emit(
                    SettingScreenUiState.Success(
                        SettingScreenUiModel(
                            didCredit,
                            elevenLabCredit,
                            totalVoice
                        )
                    )
                )
            }
        }
    }
}

sealed class SettingScreenUiState {
    object Loading : SettingScreenUiState()
    data class Failure(val message: String) : SettingScreenUiState()
    data class Success(val data: SettingScreenUiModel) : SettingScreenUiState()
}