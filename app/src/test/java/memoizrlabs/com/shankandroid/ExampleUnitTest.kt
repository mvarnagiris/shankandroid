package memoizrlabs.com.shankandroid

import com.memoizr.assertk.expect
import life.shank.Scope
import life.shank.android.ScopeObservable
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    val scope = Scope("foo")

    @Test
    fun `gets scope callback`() {
        var s: Scope? = null

        ScopeObservable.getScope(0) {
            s = it
        }

        ScopeObservable.putScope(0, scope)

        expect that s isEqualTo scope
    }

    @Test
    fun `gets latest scope`() {
        ScopeObservable.getScope(0) { }
        ScopeObservable.putScope(0, scope)

        var s: Scope? = null
        ScopeObservable.getScope(0) { s = it }

        expect that s isEqualTo scope
    }

    @Test
    fun `clears scope`() {
        ScopeObservable.getScope(0) { }
        ScopeObservable.putScope(0, scope)

        ScopeObservable.clear(0)

        var s: Scope? = null
        ScopeObservable.getScope(0) { s = it }

        expect that s _is null
    }
}

