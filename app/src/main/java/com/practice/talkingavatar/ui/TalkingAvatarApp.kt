package com.practice.talkingavatar.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.practice.talkingavatar.ui.homeScreen.HomeScreen
import com.practice.talkingavatar.ui.settingScreen.SettingScreen
import com.practice.talkingavatar.utils.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TalkingAvatarApp(
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.onBackground,
    ) {
        NavHost(
            modifier = Modifier.padding(it),
            navController = navController,
            startDestination = Screens.HOME.name
        ) {
            composable(route = Screens.HOME.name) {
                HomeScreen(navController = navController)
            }
            composable(route = Screens.SETTING.name) {
                SettingScreen(navController = navController)
            }
        }
    }
}