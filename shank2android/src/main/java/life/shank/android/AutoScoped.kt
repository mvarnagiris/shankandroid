package life.shank.android

import life.shank.*
import life.shank.android.ScopeHelper.doScopedInternal


interface AutoScoped {
    fun doScoped(onScopeReady: (Scope) -> Unit) = doScopedInternal(onScopeReady)

    operator fun <T> ScopedProvider0<T>.invoke(o: (T) -> Unit = {}) = doScoped { invoke(it).also { o(it) } }
    operator fun <A, T> ScopedProvider1<A, T>.invoke(a: A, o: (T) -> Unit = {}) =
        doScoped { invoke(it, a).also { o(it) } }

    operator fun <A, B, T> ScopedProvider2<A, B, T>.invoke(a: A, b: B, o: (T) -> Unit = {}) =
        doScoped { invoke(it, a, b).also { o(it) } }

    operator fun <A, B, C, T> ScopedProvider3<A, B, C, T>.invoke(a: A, b: B, c: C, o: (T) -> Unit = {}) =
        doScoped { invoke(it, a, b, c).also { o(it) } }
}