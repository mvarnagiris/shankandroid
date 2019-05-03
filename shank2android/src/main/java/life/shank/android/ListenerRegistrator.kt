package life.shank.android

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import life.shank.*
import java.util.*

abstract class ListenerRegistrator<V, T : AttachListener<V>> :
    Provider<T, Function<T>> where  V : Attachable {
    protected val listeners = IdentityHashMap<Any, Any>()
    protected val action = { listeners.clear() }

    class ViewCachedListener<V : Attachable, T : AttachListener<V>>(var attachListener: T) : View.OnAttachStateChangeListener {
        override fun onViewAttachedToWindow(v: View?): Unit = attachListener.onAttach(v as V)
        override fun onViewDetachedFromWindow(v: View?): Unit = attachListener.onDetach(v as V)
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

    protected inline fun T.registerAutoDetacheable(v: V): T = also { attachListener ->
        (v as Scoped)
        when {
            v is View -> {
                listeners[v]
                    ?.let { it as ViewCachedListener<V, T> }
                    ?.apply {
                        this.attachListener = attachListener
                    }
                    ?: ViewCachedListener(attachListener).also {
                        listeners[v] = it
                        v.addOnAttachStateChangeListener(it)
                        v.scope.addOnClearAction(action)
                    }
            }
            v is LifecycleOwner -> {
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
                            v.scope.addOnClearAction(action)
                        }
            }
        }
    }
}

class NewAutoAttachListenerProvider0<V : Attachable, T : AttachListener<V>>(val provider: NewProvider0<T>) : ListenerRegistrator<V, T>() {
    inline fun bind(v: V) = provider.invoke().registerAutoDetacheable(v)
}

class NewAutoAttachListenerProvider1<A, V : Attachable, T : AttachListener<V>>(val provider: NewProvider1<A, T>) : ListenerRegistrator<V, T>() {
    inline fun bind(v: V, a: A) = provider.invoke(a).registerAutoDetacheable(v)
}

class NewAutoAttachListenerProvider2<A, B, V : Attachable, T : AttachListener<V>>(val provider: NewProvider2<A, B, T>) : ListenerRegistrator<V, T>() {
    inline fun bind(v: V, a: A, b: B) = provider.invoke(a, b).registerAutoDetacheable(v)
}

class NewAutoAttachListenerProvider3<A, B, C, V : Attachable, T : AttachListener<V>>(val provider: NewProvider3<A, B, C, T>) : ListenerRegistrator<V, T>() {
    inline fun bind(v: V, a: A, b: B, c: C) = provider.invoke(a, b, c).registerAutoDetacheable(v)
}

class NewAutoAttachListenerProvider4<A, B, C, D, V : Attachable, T : AttachListener<V>>(val provider: NewProvider4<A, B, C, D, T>) :
    ListenerRegistrator<V, T>() {
    inline fun bind(v: V, a: A, b: B, c: C, d: D) = provider.invoke(a, b, c, d).registerAutoDetacheable(v)
}

class NewAutoAttachListenerProvider5<A, B, C, D, E, V : Attachable, T : AttachListener<V>>(val provider: NewProvider5<A, B, C, D, E, T>) :
    ListenerRegistrator<V, T>() {
    inline fun bind(v: V, a: A, b: B, c: C, d: D, e: E) = provider.invoke(a, b, c, d, e).registerAutoDetacheable(v)
}



class ScopedAutoAttachListenerProvider0<V : Attachable, T : AttachListener<V>>(val provider: ScopedProvider<T>) : ListenerRegistrator<V, T>() {
    inline fun bind(s: Scope, v: V) = provider.invoke(s).registerAutoDetacheable(v)
}

class ScopedAutoAttachListenerProvider1<A, V : Attachable, T : AttachListener<V>>(val provider: ScopedProvider1<A, T>) : ListenerRegistrator<V, T>() {
    inline fun bind(s: Scope, v: V, a: A) = provider.invoke(s, a).registerAutoDetacheable(v)
}

class ScopedAutoAttachListenerProvider2<A, B, V : Attachable, T : AttachListener<V>>(val provider: ScopedProvider2<A, B, T>) : ListenerRegistrator<V, T>() {
    inline fun bind(s: Scope, v: V, a: A, b: B) = provider.invoke(s, a, b).registerAutoDetacheable(v)
}

class ScopedAutoAttachListenerProvider3<A, B, C, V : Attachable, T : AttachListener<V>>(val provider: ScopedProvider3<A, B, C, T>) : ListenerRegistrator<V, T>() {
    inline fun bind(s: Scope, v: V, a: A, b: B, c: C) = provider.invoke(s, a, b, c).registerAutoDetacheable(v)
}

class ScopedAutoAttachListenerProvider4<A, B, C, D, V : Attachable, T : AttachListener<V>>(val provider: ScopedProvider4<A, B, C, D, T>) : ListenerRegistrator<V, T>() {
    inline fun bind(s: Scope, v: V, a: A, b: B, c: C, d: D) = provider.invoke(s, a, b, c, d).registerAutoDetacheable(v)
}

class ScopedAutoAttachListenerProvider5<A, B, C, D, E, V : Attachable, T : AttachListener<V>>(val provider: ScopedProvider5<A, B, C, D, E, T>) : ListenerRegistrator<V, T>() {
    inline fun bind(s: Scope, v: V, a: A, b: B, c: C, d: D, e: E) = provider.invoke(s, a, b, c, d, e).registerAutoDetacheable(v)
}
