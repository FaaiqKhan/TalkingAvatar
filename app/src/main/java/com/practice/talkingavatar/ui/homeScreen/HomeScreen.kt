package com.practice.talkingavatar.ui.homeScreen

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.*
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.practice.talkingavatar.ui.common.*
import com.practice.talkingavatar.utils.*
import kotlinx.coroutines.launch
import java.nio.file.*
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: TalkingAvatarViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    var isCamera by remember { mutableStateOf(false) }

    if (isCamera) {
        val uri = Utils.getFileUri(context = context)
        CameraButtonView(
            imageUri = uri,
            onClick = {
                isCamera = false
                viewModel.createPresenter(it, context)
            }
        )
        return
    }

    val uiState by viewModel.screenState.collectAsState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            println("ok")
        } else {
            println("not ok")
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(onClick = {
                scope.launch { drawerState.close() }
                navController.navigate(Screens.SETTING.name)
            })
        }
    ) {
        Column(modifier = modifier) {
            IconButton(
                onClick = {
                    PermissionUtils.checkAndRequestWriteExternalStorage(
                        context,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        launcher
                    )
                    scope.launch {
                        drawerState.apply {
                            if (isClosed) open()
                            else close()
                        }
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            }
            HomeScreenContent(
                screenState = uiState,
                onClick = { isCamera = !isCamera },
                exoPlayer = viewModel.exoPlayer,
                idleAnimation = viewModel.idleAnimation
            )
        }
    }
}

@Composable
fun HomeScreenContent(
    idleAnimation: String,
    screenState: HomeScreenUiState,
    onClick: () -> Unit,
    exoPlayer: ExoPlayer
) {
    var isProcessing by remember { mutableStateOf(false) }

    when (screenState) {
        is HomeScreenUiState.Idle -> {
            exoPlayer.playlistMetadata = MediaMetadata.Builder().setTitle("idleAnimation").build()
            exoPlayer.setMediaItem(MediaItem.fromUri(Uri.parse(idleAnimation)))
            exoPlayer.repeatMode = Player.REPEAT_MODE_ONE
        }

        is HomeScreenUiState.Loading -> {
            isProcessing = true

        }

        is HomeScreenUiState.Failure -> {
            isProcessing = false

        }

        is HomeScreenUiState.Success -> {
            isProcessing = false
            exoPlayer.playlistMetadata = MediaMetadata.Builder().setTitle("animation").build()
            exoPlayer.setMediaItem(MediaItem.fromUri(Uri.parse(screenState.model.animation)))
            exoPlayer.repeatMode = Player.REPEAT_MODE_OFF
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        VideoPlayer(
            exoPlayer = exoPlayer,
            modifier = Modifier.border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(percent = 100)
            ),
        )
        if (isProcessing) {
            ProgressIndicator()
        } else {
            Text(
                text = "Let's create your avatar today!",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center
            )
            TalkingAvatarTextButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Get started",
                onClick = onClick,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerContent(onClick: () -> Unit) {
    ModalDrawerSheet(
        modifier = Modifier.width(300.dp),
        drawerShape = RectangleShape,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(180.dp)
                    .padding(top = 20.dp)
                    .clip(RoundedCornerShape(100)),
                model = Utils.getApplicationLogo(LocalContext.current),
                contentDescription = "Application logo",
                contentScale = ContentScale.Fit,
            )
            Text(
                Utils.getApplicationName(LocalContext.current),
                modifier = Modifier.padding(16.dp)
            )
            Divider()
            NavigationDrawerItem(
                label = { Text(text = "Drawer Item") },
                selected = false,
                onClick = { }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .clickable { onClick() },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings"
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "Settings")
        }
    }
}