package com.practice.talkingavatar.ui.common

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practice.talkingavatar.ui.theme.TalkingAvatarTheme

@Composable
fun ProgressIndicator(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        shape = RoundedCornerShape(percent = 100)
    ) {
        SpinningProgressBar(
            modifier = Modifier.padding(all = 12.dp),
            contentColor = MaterialTheme.colorScheme.onBackground,
            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
            spacing = 10
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ProgressIndicatorPreview() {
    TalkingAvatarTheme {
        ProgressIndicator()
    }
}