package com.example.galaxyshooter.audio

import android.content.Context
import android.media.SoundPool

class SoundManager(context: Context) {
    private val pool = SoundPool.Builder().setMaxStreams(8).build()
    fun play(resId: Int) = pool.play(resId, 1f, 1f, 1, 0, 1f)
    fun release() = pool.release()
}
