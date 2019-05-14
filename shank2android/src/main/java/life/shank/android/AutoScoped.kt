package life.shank.android

import android.view.View
import androidx.lifecycle.LifecycleOwner
import life.shank.Scope
import life.shank.ScopedProvider0
import life.shank.ScopedProvider1
import life.shank.ScopedProvider2
import life.shank.ScopedProvider3


interface AutoScoped {

    fun onScopeReady(block: (Scope) -> Unit) {
        when (this) {
            is LifecycleOwner -> ScopeObservable.getScope(hashCode(), block)
            is View -> ScopeHelper.findScopeForView(this, block)
            else -> throw IllegalArgumentException()
        }
    }

    operator fun <T> ScopedProvider0<T>.invoke(o: (T) -> Unit = {}) =
        onScopeReady { invoke(it).also { o(it) } }

    operator fun <A, T> ScopedProvider1<A, T>.invoke(a: A, o: (T) -> Unit = {}) =
        onScopeReady { invoke(it, a).also { o(it) } }

    operator fun <A, B, T> ScopedProvider2<A, B, T>.invoke(a: A, b: B, o: (T) -> Unit = {}) =
        onScopeReady { invoke(it, a, b).also { o(it) } }

    operator fun <A, B, C, T> ScopedProvider3<A, B, C, T>.invoke(a: A, b: B, c: C, o: (T) -> Unit = {}) =
        onScopeReady { invoke(it, a, b, c).also { o(it) } }
}