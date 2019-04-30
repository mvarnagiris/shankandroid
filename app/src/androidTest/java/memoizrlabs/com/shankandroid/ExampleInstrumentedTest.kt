package memoizrlabs.com.shankandroid

import android.os.Debug
import androidx.test.ext.junit.runners.AndroidJUnit4
import life.shank.ShankModule
import life.shank.android.AppContextModule
import life.shank.new
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

//    @Rule
//    var repeatRule = RepeatRule()

    class xx {
        @JvmField
        val hashCode = hashCode()
    }
    @Test
    fun useAppContext() {
        val a = xx()
        // Context of the app under test.
        Debug.startMethodTracing("sample")
        Module.a2()
        Module.a2()
        AppContextModule.appContext()
        Debug.stopMethodTracing()
    }
}

@JvmField val s = "dlfjdlkjf"

object Module: ShankModule {
    @JvmField val a2 = new { -> s }
    @JvmField val a3 = new { -> s }
    @JvmField val a4 = new { -> s }
    @JvmField val a5 = new { -> s }
    @JvmField val a6 = new { -> s }
    @JvmField val a7 = new { -> s }
    @JvmField val a8 = new { -> s }
}

