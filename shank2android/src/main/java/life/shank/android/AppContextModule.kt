package life.shank.android

import android.content.Context
import life.shank.ShankModule
import life.shank.singleton

object AppContextModule: ShankModule {
    internal lateinit var context: Context
    val appContext = singleton { -> context }
}