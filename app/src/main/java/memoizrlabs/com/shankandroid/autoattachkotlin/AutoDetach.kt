package memoizrlabs.com.shankandroid.autoattachkotlin

import life.shank.Scoped


interface AutoDetachable : Scoped

interface DetachAware<V : AutoDetachable> {
    fun attach(v: V)
    fun detach(v: V)
}
