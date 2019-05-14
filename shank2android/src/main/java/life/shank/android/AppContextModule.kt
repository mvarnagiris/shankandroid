package life.shank.android

import android.annotation.SuppressLint
import android.content.Context
import life.shank.ShankModule
import life.shank.singleton

@SuppressLint("StaticFieldLeak")
object AppContextModule : ShankModule {
    internal lateinit var context: Context
    val appContext = singleton { -> context }
}