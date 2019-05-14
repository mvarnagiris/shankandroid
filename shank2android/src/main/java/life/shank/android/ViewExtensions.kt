package life.shank.android

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.View
import androidx.appcompat.view.ContextThemeWrapper

internal val View.activity: Activity? get() = context.activity
private val Context.activity: Activity?
    get() = when (this) {
        is Activity -> this
        is ContextThemeWrapper -> this.baseContext.activity
        is android.view.ContextThemeWrapper -> this.baseContext.activity
        is ContextWrapper -> this.baseContext.activity
        else -> null
    }