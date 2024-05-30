package com.mckimquyen.barcodescanner.extension

import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

fun Vibrator.vibrateOnce(pattern: LongArray) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrate(VibrationEffect.createWaveform(
            /* timings = */ pattern,
            /* repeat = */ -1
        ))
    } else {
        vibrate(
            /* pattern = */ pattern,
            /* repeat = */ -1
        )
    }
}
