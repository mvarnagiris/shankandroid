package life.shank.android

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import life.shank.AttachListener
import life.shank.Attachable
import life.shank.NewProvider0
import life.shank.NewProvider1
import life.shank.NewProvider2
import life.shank.NewProvider3
import life.shank.NewProvider4
import life.shank.NewProvider5
import life.shank.Scope
import life.shank.ScopedProvider0
import life.shank.ScopedProvider1
import life.shank.ScopedProvider2
import life.shank.ScopedProvider3
import java.util.IdentityHashMap
import kotlin.collections.set

abstract class ListenerRegistrator<V : Attachable, T : AttachListener<V>> {
    protected val listeners = IdentityHashMap<Any, Any>()
    protected val action = { listeners.clear() }

    interface ViewCachedListener<V : Attachable, T : AttachListener<V>> : View.OnAttachStateChangeListener {
        var attachListener: T
    }

    class ReusableViewCachedListener<V : Attachable, T : AttachListener<V>>(override var attachListener: T) :
        ViewCachedListener<V, T> {
        override fun onViewAttachedToWindow(v: View?): Unit = attachListener.onAttach(v as V)
        override fun onViewDetachedFromWindow(v: View?): Unit = attachListener.onDetach(v as V)
    }

    class SingleUseViewCachedListener<V : Attachable, T : AttachListener<V>>(override var attachListener: T) :
        ViewCachedListener<V, T> {
        override fun onViewAttachedToWindow(v: View?): Unit = attachListener.onAttach(v as V)
        override fun onViewDetachedFromWindow(v: View?): Unit =
            attachListener.onDetach(v as V).also { v.removeOnAttachStateChangeListener(this) }
    }

    class LifecycleCachedListener<V : Attachable, T : AttachListener<V>>(var attachListener: T, var v: V) :
        LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        fun onResume() {
            attachListener.onAttach(v)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        fun onPause() {
            attachListener.onDetach(v)
        }
    }

    protected inline fun T.registerSingleUseAutoDetacheable(v: V): T = also { attachListener ->
        when (v) {
            is View -> {
                if (v.isAttachedToWindow) {
                    attachListener.onAttach(v)
                }
                SingleUseViewCachedListener(attachListener).also { v.addOnAttachStateChangeListener(it) }
            }
            is LifecycleOwner -> LifecycleCachedListener(attachListener, v).also { v.lifecycle.addObserver(it) }

        }
    }

    protected inline fun T.registerCachedAutoDetacheable(v: V): T = also { attachListener ->
        (v as AutoScoped)
        when (v) {
            is View -> {
                if (v.isAttachedToWindow) {
                    attachListener.onAttach(v)
                }
                listeners[v]
                    ?.let { it as ViewCachedListener<V, T> }
                    ?.apply {
                        this.attachListener = attachListener
                    }
                    ?: ReusableViewCachedListener(attachListener).also {
                        listeners[v] = it
                        v.addOnAttachStateChangeListener(it)
                        v.onScopeReady {
                            it.addOnClearAction(action)
                        }
                    }
            }
            is LifecycleOwner -> {
                listeners[v]
                    ?.let { it as LifecycleCachedListener<V, T> }
                    ?.apply {
                        this.v = v
                        this.attachListener = attachListener
                    }
                    ?: LifecycleCachedListener(attachListener, v)
                        .also {
                            listeners[v] = it
                            v.lifecycle.addObserver(it)
                            v.onScopeReady {
                                it.addOnClearAction(action)
                            }
                        }
            }
        }
    }
}

class NewAutoAttachListenerProvider0<V : Attachable, T : AttachListener<V>>(provider: NewProvider0<T>) :
    ListenerRegistrator<V, T>(), NewProvider0<T> by provider {
    fun register(v: V) = invoke().registerSingleUseAutoDetacheable(v)
}

class NewAutoAttachListenerProvider1<A, V : Attachable, T : AttachListener<V>>(provider: NewProvider1<A, T>) :
    ListenerRegistrator<V, T>(), NewProvider1<A, T> by provider {
    fun register(v: V, a: A) = invoke(a).registerSingleUseAutoDetacheable(v)
}

class NewAutoAttachListenerProvider2<A, B, V : Attachable, T : AttachListener<V>>(provider: NewProvider2<A, B, T>) :
    ListenerRegistrator<V, T>(), NewProvider2<A, B, T> by provider {
    fun register(v: V, a: A, b: B) = invoke(a, b).registerSingleUseAutoDetacheable(v)
}

class NewAutoAttachListenerProvider3<A, B, C, V : Attachable, T : AttachListener<V>>(provider: NewProvider3<A, B, C, T>) :
    ListenerRegistrator<V, T>(), NewProvider3<A, B, C, T> by provider {
    fun register(v: V, a: A, b: B, c: C) = invoke(a, b, c).registerSingleUseAutoDetacheable(v)
}

class NewAutoAttachListenerProvider4<A, B, C, D, V : Attachable, T : AttachListener<V>>(provider: NewProvider4<A, B, C, D, T>) :
    ListenerRegistrator<V, T>(), NewProvider4<A, B, C, D, T> by provider {
    fun register(v: V, a: A, b: B, c: C, d: D) = invoke(a, b, c, d).registerSingleUseAutoDetacheable(v)
}

class NewAutoAttachListenerProvider5<A, B, C, D, E, V : Attachable, T : AttachListener<V>>(provider: NewProvider5<A, B, C, D, E, T>) :
    ListenerRegistrator<V, T>(), NewProvider5<A, B, C, D, E, T> by provider {
    fun register(v: V, a: A, b: B, c: C, d: D, e: E) = invoke(a, b, c, d, e).registerSingleUseAutoDetacheable(v)
}

class ScopedAutoAttachListenerProvider0<V : Attachable, T : AttachListener<V>>(provider: ScopedProvider0<T>) :
    ListenerRegistrator<V, T>(), ScopedProvider0<T> by provider {
    fun register(scope: Scope, v: V) = invoke(scope).registerCachedAutoDetacheable(v)
}

class ScopedAutoAttachListenerProvider1<A, V : Attachable, T : AttachListener<V>>(provider: ScopedProvider1<A, T>) :
    ListenerRegistrator<V, T>(), ScopedProvider1<A, T> by provider {
    fun register(scope: Scope, v: V, a: A) = invoke(scope, a).registerCachedAutoDetacheable(v)
}

class ScopedAutoAttachListenerProvider2<A, B, V : Attachable, T : AttachListener<V>>(provider: ScopedProvider2<A, B, T>) :
    ListenerRegistrator<V, T>(), ScopedProvider2<A, B, T> by provider {
    fun register(scope: Scope, v: V, a: A, b: B) = invoke(scope, a, b).registerCachedAutoDetacheable(v)
}

class ScopedAutoAttachListenerProvider3<A, B, C, V : Attachable, T : AttachListener<V>>(provider: ScopedProvider3<A, B, C, T>) :
    ListenerRegistrator<V, T>(), ScopedProvider3<A, B, C, T> by provider {
    fun register(scope: Scope, v: V, a: A, b: B, c: C) = invoke(scope, a, b, c).registerCachedAutoDetacheable(v)
}
