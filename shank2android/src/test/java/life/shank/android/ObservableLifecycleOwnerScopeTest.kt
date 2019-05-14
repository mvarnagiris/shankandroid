package life.shank.android

import androidx.fragment.app.Fragment
import com.memoizr.assertk.expect
import life.shank.Scope
import org.junit.Test

class ObservableLifecycleOwnerScopeTest {

    val scope = Scope("foo")
    val lifecycleOwner = Fragment()

    @Test
    fun `gets scope callback`() {
        var s: Scope? = null

        ObservableLifecycleOwnerScope.doOnScopeReady(lifecycleOwner) {
            s = it
        }

        ObservableLifecycleOwnerScope.putScope(lifecycleOwner, scope)

        expect that s isEqualTo scope
    }

    @Test
    fun `gets latest scope`() {
        ObservableLifecycleOwnerScope.doOnScopeReady(lifecycleOwner) { }
        ObservableLifecycleOwnerScope.putScope(lifecycleOwner, scope)

        var s: Scope? = null
        ObservableLifecycleOwnerScope.doOnScopeReady(lifecycleOwner) { s = it }

        expect that s isEqualTo scope
    }

    @Test
    fun `clears scope`() {
        ObservableLifecycleOwnerScope.doOnScopeReady(lifecycleOwner) { }
        ObservableLifecycleOwnerScope.putScope(lifecycleOwner, scope)

        ObservableLifecycleOwnerScope.clear(lifecycleOwner)

        var s: Scope? = null
        ObservableLifecycleOwnerScope.doOnScopeReady(lifecycleOwner) { s = it }

        expect that s _is null
    }

}