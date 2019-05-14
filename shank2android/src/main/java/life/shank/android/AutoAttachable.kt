package life.shank.android

import life.shank.AttachListener
import life.shank.Attachable

interface AutoAttachable : Attachable, AutoScoped {
    fun <V : Attachable, T : AttachListener<V>> ScopedAutoAttachListenerProvider0<V, T>.register(v: V) = onScopeReady {  register(it, v) }
    fun <A, V : Attachable, T : AttachListener<V>> ScopedAutoAttachListenerProvider1<A, V, T>.register(v: V, a: A) = onScopeReady {  register(it, v, a) }
    fun <A, B, V : Attachable, T : AttachListener<V>> ScopedAutoAttachListenerProvider2<A, B, V, T>.register(v: V, a: A, b: B) = onScopeReady {  register(it, v, a, b) }
    fun <A, B, C, V : Attachable, T : AttachListener<V>> ScopedAutoAttachListenerProvider3<A, B, C, V, T>.register(v: V, a: A, b: B, c: C) = onScopeReady {  register(it, v, a, b, c) }
}