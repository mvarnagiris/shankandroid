package life.shank.android

import life.shank.Scope

object ScopeObservable {
    private val callbacks = mutableListOf<Pair<Int, (Scope) -> Unit>>()
    private val cached = mutableMapOf<Int, Scope>()

    fun getScope(hash: Int, callback: (Scope) -> Unit) {
        cached[hash]?.also { callback(it) } ?: callbacks.add(hash to callback)
    }

    fun putScope(hash: Int, scope: Scope) {
        cached[hash] = scope
        callbacks.filter { it.first == hash }
            .forEach {
                it.second(scope)
            }
    }

    fun clear(hash: Int) {
        cached.remove(hash)
        callbacks.removeAll { it.first == hash }
    }
}