package life.shank.android

import life.shank.*

fun <V : Attachable, T : AttachListener<V>> ScopedProvider<T>.supportAutoAttach() = ScopedAutoAttachListenerProvider0(this)
fun <A, V : Attachable, T : AttachListener<V>> ScopedProvider1<A, T>.supportAutoAttach() = ScopedAutoAttachListenerProvider1(this)
fun <A, B, V : Attachable, T : AttachListener<V>> ScopedProvider2<A, B, T>.supportAutoAttach() = ScopedAutoAttachListenerProvider2(this)
fun <A, B, C, V : Attachable, T : AttachListener<V>> ScopedProvider3<A, B, C, T>.supportAutoAttach() = ScopedAutoAttachListenerProvider3(this)
fun <A, B, C, D, V : Attachable, T : AttachListener<V>> ScopedProvider4<A, B, C, D, T>.supportAutoAttach() = ScopedAutoAttachListenerProvider4(this)
fun <A, B, C, D, E, V : Attachable, T : AttachListener<V>> ScopedProvider5<A, B, C, D, E, T>.supportAutoAttach() = ScopedAutoAttachListenerProvider5(this)

fun <V : Attachable, T : AttachListener<V>> NewProvider0<T>.supportAutoAttach() = NewAutoAttachListenerProvider0(this)
fun <A, V : Attachable, T : AttachListener<V>> NewProvider1<A, T>.supportAutoAttach() = NewAutoAttachListenerProvider1(this)
fun <A, B, V : Attachable, T : AttachListener<V>> NewProvider2<A, B, T>.supportAutoAttach() = NewAutoAttachListenerProvider2(this)
fun <A, B, C, V : Attachable, T : AttachListener<V>> NewProvider3<A, B, C, T>.supportAutoAttach() = NewAutoAttachListenerProvider3(this)
fun <A, B, C, D, V : Attachable, T : AttachListener<V>> NewProvider4<A, B, C, D, T>.supportAutoAttach() = NewAutoAttachListenerProvider4(this)
fun <A, B, C, D, E, V : Attachable, T : AttachListener<V>> NewProvider5<A, B, C, D, E, T>.supportAutoAttach() = NewAutoAttachListenerProvider5(this)
