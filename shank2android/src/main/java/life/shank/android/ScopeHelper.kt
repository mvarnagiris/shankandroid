package life.shank.android

import android.app.Activity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import life.shank.Scope
import life.shank.Scoped
import java.util.IdentityHashMap
import java.util.UUID

private val scopeMap = IdentityHashMap<Any, Scope>()

internal object ScopeHelper {
    @JvmStatic
    internal inline fun AutoScoped.getCachedScope(): Scope {
        return scopeMap[this] ?: when (this) {
            is View -> findParentScopeForView(this)
            is Activity -> activityAutoScopeCache[this]!!
            is Fragment -> fragmentAutoScopeCache[this]!!
            else -> throw IllegalArgumentException()
        }.also { scopeMap[this] = it.addOnClearAction { scopeMap.remove(this) } }
    }

    internal inline fun newScope() = Scope(UUID.randomUUID())

    private inline fun findParentScopeForView(view: View): Scope {
        val parentViewScope = view.findScopeInParentView()
        if (parentViewScope != null) return parentViewScope

        if (view.id == View.NO_ID) throw IllegalArgumentException("View must have an id $view")
        val activity = view.activity as? AppCompatActivity
            ?: throw IllegalArgumentException("View does not have an AppCompatActivity $view")
        val fragmentManager = activity.supportFragmentManager

        val parentFragmentScope = fragmentManager.findScopeInFragmentClosestToTheView(view)
        if (parentFragmentScope != null) return parentFragmentScope

        if (activity is Scoped) return activity.scope

        throw IllegalArgumentException("View does not have any parent scopes $view")
    }

    private fun FragmentManager.findScopeInFragmentClosestToTheView(view: View): Scope? {
        val scopedFragment = fragments.firstOrNull {
            val scopedChildFragment = it.childFragmentManager.findScopeInFragmentClosestToTheView(view)
            if (scopedChildFragment != null) return scopedChildFragment

            it.containsView(view) && it is Scoped
        } as? Scoped
        return scopedFragment?.scope
    }

    private fun View.findScopeInParentView(): Scope? {
        val parentView = parent as? View
        if (parentView != null) {
            if (parentView is Scoped) return parentView.scope
            return parentView.findScopeInParentView()
        }
        return null
    }

    private inline fun Fragment.containsView(view: View) = view == this.view?.findViewById(view.id)
}