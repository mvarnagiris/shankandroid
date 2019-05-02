package life.shank.android

import android.view.View
import life.shank.*
import life.shank.android.Helper.scopee
import java.util.*

fun <V : AutoDetachable, T : DetachAware<V>> ScopedProvider<T>.detachAware() = Detach0(this)
fun <A, V : AutoDetachable, T : DetachAware<V>> ScopedProvider1<A, T>.detachAware() = Detach1(this)
fun <A, B, V : AutoDetachable, T : DetachAware<V>> ScopedProvider2<A, B, T>.detachAware() =
    Detach2(this)
fun <A, B, C, V : AutoDetachable, T : DetachAware<V>> ScopedProvider3<A, B, C, T>.detachAware() =
    Detach3(this)
fun <A, B, C, D, V : AutoDetachable, T : DetachAware<V>> ScopedProvider4<A, B, C, D, T>.detachAware() =
    Detach4(this)
fun <A, B, C, D, E, V : AutoDetachable, T : DetachAware<V>> ScopedProvider5<A, B, C, D, E, T>.detachAware() =
    Detach5(this)

class Detach0<V : AutoDetachable, T : DetachAware<V>>(val p: ScopedProvider<T>) : IDetatch<V, T>() {
    inline fun bind(s: Scope, v: V) = p.invoke(s).registerAutoDetacheable(v)
}

class Detach1<A, V : AutoDetachable, T : DetachAware<V>>(val p: ScopedProvider1<A, T>) : IDetatch<V, T>() {
    inline fun bind(s: Scope, v: V, a: A) = p.invoke(s, a).registerAutoDetacheable(v)
}

class Detach2<A, B, V : AutoDetachable, T : DetachAware<V>>(val p: ScopedProvider2<A, B, T>) : IDetatch<V, T>() {
    inline fun bind(s: Scope, v: V, a: A, b: B) = p.invoke(s, a, b).registerAutoDetacheable(v)
}

class Detach3<A, B, C, V : AutoDetachable, T : DetachAware<V>>(val p: ScopedProvider3<A, B, C, T>) : IDetatch<V, T>() {
    inline fun bind(s: Scope, v: V, a: A, b: B, c: C) = p.invoke(s, a, b, c).registerAutoDetacheable(v)
}

class Detach4<A, B, C, D, V : AutoDetachable, T : DetachAware<V>>(val p: ScopedProvider4<A, B, C, D, T>) :
    IDetatch<V, T>() {
    inline fun bind(s: Scope, v: V, a: A, b: B, c: C, d: D) = p.invoke(s, a, b, c, d).registerAutoDetacheable(v)
}

class Detach5<A, B, C, D, E, V : AutoDetachable, T : DetachAware<V>>(val p: ScopedProvider5<A, B, C, D, E, T>) :
    IDetatch<V, T>() {
    inline fun bind(s: Scope, v: V, a: A, b: B, c: C, d: D, e: E) =
        p.invoke(s, a, b, c, d, e).registerAutoDetacheable(v)
}

abstract class IDetatch<V, T : DetachAware<V>> : Provider<T, Function<T>> where  V : AutoDetachable {
    protected val listeners = IdentityHashMap<View, O<V, T>>()
    protected val action = { listeners.clear() }

    class O<V : AutoDetachable, T : DetachAware<V>>(var it: T) : View.OnAttachStateChangeListener {
        inline override fun onViewAttachedToWindow(v: View?): Unit = it.attach(v as V)
        inline override fun onViewDetachedFromWindow(v: View?): Unit = it.detach(v as V)
    }

    protected inline fun T.registerAutoDetacheable(v: V): T = also { detachAware ->
        (v as View)
        (v as Scoped)
        listeners[v]
            ?.apply { this.it = detachAware }
            ?: O(detachAware).also {
                listeners[v] = it
                v.addOnAttachStateChangeListener(it)
                v.scope.addOnClearAction(action)
            }
    }
}

interface AutoAttached : AutoDetachable, AutoScoped {
    fun <V : AutoDetachable, T : DetachAware<V>> Detach0<V, T>.bind(v: V) = bind(scopee(), v)
    fun <A, V : AutoDetachable, T : DetachAware<V>> Detach1<A, V, T>.bind(v: V, a: A) = bind(scopee(), v, a)
    fun <A, B, V : AutoDetachable, T : DetachAware<V>> Detach2<A, B, V, T>.bind(v: V, a: A, b: B) =
        bind(scopee(), v, a, b)

    fun <A, B, C, V : AutoDetachable, T : DetachAware<V>> Detach3<A, B, C, V, T>.bind(v: V, a: A, b: B, c: C) =
        bind(scope, v, a, b, c)

    fun <A, B, C, D, V : AutoDetachable, T : DetachAware<V>> Detach4<A, B, C, D, V, T>.bind(
        v: V,
        a: A,
        b: B,
        c: C,
        d: D
    ) = bind(scope, v, a, b, c, d)

    fun <A, B, C, D, E, V : AutoDetachable, T : DetachAware<V>> Detach5<A, B, C, D, E, V, T>.bind(
        v: V,
        a: A,
        b: B,
        c: C,
        d: D,
        e: E
    ) = bind(scope, v, a, b, c, d, e)
}

interface Bindable<V : AutoDetachable> : AutoDetachable, AutoScoped {
    fun <A, T : DetachAware<V>> Detach1<A, V, T>.bind(a: A) = bind(scope, this@Bindable as V, a)
    fun <A, T : DetachAware<V>> Detach1<A, V, T>.bind(v: V, a: A) = bind(scope, v, a)
}

