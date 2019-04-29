package memoizrlabs.com.shankandroid

import android.os.Debug
import androidx.test.ext.junit.runners.AndroidJUnit4
import life.shank.ShankModule
import life.shank.new
import life.shank.android.AppContextModule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

//    @Rule
//    var repeatRule = RepeatRule()

    @Test
    fun useAppContext() {
        // Context of the app under test.
        Debug.startMethodTracing("sample")
        Module.a2()
        AppContextModule.appContext()
        Debug.stopMethodTracing()
    }
}

val s = "dlfjdlkjf"

object Module: ShankModule {
    val a2 = new { -> s }
    val a3 = new { -> s }
    val a4 = new { -> s }
    val a5 = new { -> s }
    val a6 = new { -> s }
    val a7 = new { -> s }
    val a8 = new { -> s }
}

