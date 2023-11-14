package com.practice.talkingavatar.ui.settingScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.practice.talkingavatar.R
import com.practice.talkingavatar.ui.common.TalkingAvatarTextButton
import com.practice.talkingavatar.ui.theme.TalkingAvatarTheme

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    settingViewModel: SettingScreenViewModel = hiltViewModel(),
) {

    val uiState by settingViewModel.uiState.collectAsState()

    SettingScreenContent(
        modifier = modifier,
        onBackPress = { navController.popBackStack() },
        uiState = uiState
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreenContent(
    modifier: Modifier,
    onBackPress: () -> Unit,
    uiState: SettingScreenUiState,
) {

    var isLive by remember { mutableStateOf(false) }
    var botId by remember { mutableStateOf("") }
    var organizationId by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onBackground)
    ) {
        IconButton(onClick = onBackPress) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Go back",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Live Response",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
                Switch(checked = isLive, onCheckedChange = { isLive = it })
            }
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                value = botId,
                onValueChange = { botId = it },
                label = { Text(text = "Bot Id") }
            )
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = organizationId,
                onValueChange = { organizationId = it },
                label = { Text(text = "Organization Id") }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TalkingAvatarTextButton(
                    modifier = Modifier.width(90.dp),
                    text = "Set",
                    onClick = { })
                Spacer(modifier = Modifier.width(20.dp))
                TalkingAvatarTextButton(
                    modifier = Modifier.width(90.dp),
                    text = "Reset",
                    onClick = { })
            }
            Text(
                text = "Credits details",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary,
            )
            when (uiState) {
                is SettingScreenUiState.Loading -> Text(
                    "Loading...",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                )

                is SettingScreenUiState.Failure -> Text(
                    text = uiState.message,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                )

                is SettingScreenUiState.Success -> {
                    Text(
                        text = "Talk credits",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                    Text(
                        text = "Total: ${uiState.data.didCredit.total}",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = "Remaining: ${uiState.data.didCredit.remaining}",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = "Voice credits",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                    Text(
                        text = "Character Limit: ${uiState.data.elevenLabCredit.characterLimit}",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = "Character Count: ${uiState.data.elevenLabCredit.characterCount}",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = "Total voice: ${uiState.data.elevenLabCredit.voiceLimit}",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = "Remaining voice: ${uiState.data.totalVoices}",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
        TalkingAvatarTextButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.screen_horizontal_spacing))
                .padding(bottom = dimensionResource(id = R.dimen.bottom_spacing)),
            text = "Reset Profile",
            onClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSettingScreen() {
    TalkingAvatarTheme {
        SettingScreenContent(
            modifier = Modifier.fillMaxSize(),
            onBackPress = {},
            uiState = SettingScreenUiState.Loading
        )
    }
}