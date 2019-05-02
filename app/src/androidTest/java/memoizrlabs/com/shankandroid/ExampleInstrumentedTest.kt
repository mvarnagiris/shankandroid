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



    @Test
    fun useAppContext() {
        // Context of the app under test.
        Debug.startMethodTracing("sample")
        Module.a2()
        MyThing.a1()
        Module.a2()
        MyThing.a1()
        Module.a2()
        MyThing.a1()
        Module.a2()
        MyThing.a1()
        AppContextModule.appContext()
        Debug.stopMethodTracing()
    }
}
interface X

inline fun <T> X.new0(crossinline b: () -> T): New0<T> {
    return object : New0<T> {
        override fun override(f: () -> T): New0<T> = apply { o = f }
        private var o: (() -> T)? = null
        override fun invoke(): T = o?.invoke() ?: b()
    }
}
interface New0<T> {
    fun override(f: () -> T): New0<T>
    operator fun invoke(): T
}
object MyThing: X {
    val a1 = new0 { -> a2() }
    val a2 = new0 { -> "cuck you" }
    val a3 = new0 { -> "cuck you" }
    val a4 = new0 { -> "cuck you" }
    val a5 = new0 { -> "cuck you" }
    val a6 = new0 { -> "cuck you" }
    val a7 = new0 { -> "cuck you" }
    val a8 = new0 { -> "cuck you" }
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

