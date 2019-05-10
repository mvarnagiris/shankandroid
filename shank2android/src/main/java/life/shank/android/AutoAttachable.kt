package life.shank.android

import life.shank.AttachListener
import life.shank.Attachable
import life.shank.Scoped

interface AutoAttachable : Attachable, Scoped {
    fun <V : Attachable, T : AttachListener<V>> ScopedAutoAttachListenerProvider0<V, T>.register(v: V) = register(scope, v)
    fun <A, V : Attachable, T : AttachListener<V>> ScopedAutoAttachListenerProvider1<A, V, T>.register(v: V, a: A) = register(scope, v, a)
    fun <A, B, V : Attachable, T : AttachListener<V>> ScopedAutoAttachListenerProvider2<A, B, V, T>.register(v: V, a: A, b: B) = register(scope, v, a, b)
    fun <A, B, C, V : Attachable, T : AttachListener<V>> ScopedAutoAttachListenerProvider3<A, B, C, V, T>.register(v: V, a: A, b: B, c: C) = register(scope, v, a, b, c)
}