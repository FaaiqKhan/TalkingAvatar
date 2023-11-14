package com.practice.talkingavatar.ui.common

import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TalkingAvatarTextButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier.height(54.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.onBackground,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        content = { Text(text = text, style = MaterialTheme.typography.bodyLarge) },
        shape = MaterialTheme.shapes.medium,
        onClick = onClick,
    )
}