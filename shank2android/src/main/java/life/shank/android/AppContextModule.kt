package life.shank.android

import android.annotation.SuppressLint
import android.content.Context
import life.shank.ShankModule
import life.shank.single

@SuppressLint("StaticFieldLeak")
object AppContextModule : ShankModule {
    internal lateinit var context: Context
    val appContext = single { -> context }
}