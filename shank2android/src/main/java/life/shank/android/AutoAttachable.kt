package life.shank.android

import life.shank.AttachListener
import life.shank.Attachable
import life.shank.Scoped

interface AutoAttachable : Attachable, Scoped {
    fun <V : Attachable, T : AttachListener<V>> ScopedAutoAttachListenerProvider0<V, T>.register(v: V) = bind(scope, v)
    fun <A, V : Attachable, T : AttachListener<V>> ScopedAutoAttachListenerProvider1<A, V, T>.register(v: V, a: A) = bind(scope, v, a)
    fun <A, B, V : Attachable, T : AttachListener<V>> ScopedAutoAttachListenerProvider2<A, B, V, T>.register(v: V, a: A, b: B) = bind(scope, v, a, b)
    fun <A, B, C, V : Attachable, T : AttachListener<V>> ScopedAutoAttachListenerProvider3<A, B, C, V, T>.register(v: V, a: A, b: B, c: C) = bind(scope, v, a, b, c)
    fun <A, B, C, D, V : Attachable, T : AttachListener<V>> ScopedAutoAttachListenerProvider4<A, B, C, D, V, T>.register(v: V, a: A, b: B, c: C, d: D ) = bind(scope, v, a, b, c, d)
    fun <A, B, C, D, E, V : Attachable, T : AttachListener<V>> ScopedAutoAttachListenerProvider5<A, B, C, D, E, V, T>.register(v: V, a: A, b: B, c: C, d: D, e: E ) = bind(scope, v, a, b, c, d, e)

    fun <V : Attachable, T : AttachListener<V>> NewAutoAttachListenerProvider0<V, T>.register(v: V) = bind(v)
    fun <A, V : Attachable, T : AttachListener<V>> NewAutoAttachListenerProvider1<A, V, T>.register(v: V, a: A) = bind(v, a)
    fun <A, B, V : Attachable, T : AttachListener<V>> NewAutoAttachListenerProvider2<A, B, V, T>.register(v: V, a: A, b: B) = bind(v, a, b)
    fun <A, B, C, V : Attachable, T : AttachListener<V>> NewAutoAttachListenerProvider3<A, B, C, V, T>.register(v: V, a: A, b: B, c: C) = bind(v, a, b, c)
    fun <A, B, C, D, V : Attachable, T : AttachListener<V>> NewAutoAttachListenerProvider4<A, B, C, D, V, T>.register(v: V, a: A, b: B, c: C, d: D ) = bind(v, a, b, c, d)
    fun <A, B, C, D, E, V : Attachable, T : AttachListener<V>> NewAutoAttachListenerProvider5<A, B, C, D, E, V, T>.register(v: V, a: A, b: B, c: C, d: D, e: E ) = bind(v, a, b, c, d, e)
}