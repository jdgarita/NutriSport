package dev.jdgarita.nutrisport.shared.util

actual fun getScreenWidth(): Float =
    android.content.res.Resources.getSystem().displayMetrics.widthPixels / android.content.res.Resources.getSystem().displayMetrics.density