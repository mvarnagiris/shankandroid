package life.shank.android

import life.shank.Scope
import life.shank.Scoped
import life.shank.android.ScopeHelper.getCachedScope


interface AutoScoped : Scoped {
    override val scope: Scope get() = getCachedScope()
}