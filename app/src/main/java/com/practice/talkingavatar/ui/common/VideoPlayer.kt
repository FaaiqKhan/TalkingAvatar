package com.practice.talkingavatar.ui.common

import android.view.SurfaceHolder
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.media3.exoplayer.ExoPlayer
import com.practice.talkingavatar.databinding.FragmentBlankBinding

@Composable
fun VideoPlayer(modifier: Modifier = Modifier, exoPlayer: ExoPlayer) {
    val context = LocalContext.current
    val screenWidth = context.resources.displayMetrics.widthPixels

    AndroidViewBinding(
        modifier = modifier,
        factory = FragmentBlankBinding::inflate
    ) {
        val surfaceViewHolder = this.presenter.holder

        surfaceViewHolder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(surfaceHolder: SurfaceHolder) {
                exoPlayer.setVideoSurface(surfaceHolder.surface)
                exoPlayer.play()
            }

            override fun surfaceChanged(surfaceHolder: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
                println("Surface changed")
            }

            override fun surfaceDestroyed(surfaceHolder: SurfaceHolder) {
                exoPlayer.pause()
            }
        })

        val frameSize = (screenWidth * 0.8).toInt()
        val centerOfFrame = frameSize / 2F

        this.presenter.cropCircle(centerOfFrame, centerOfFrame, centerOfFrame.toInt())
        this.presenter.layoutParams = FrameLayout.LayoutParams(frameSize, frameSize)
        this.presenter.setOtherShape(true)
    }
}