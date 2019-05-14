package life.shank.android

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import life.shank.Scope
import java.util.UUID

internal object ScopeHelper {
    internal inline fun AutoScoped.doScopedInternal(noinline osr: (Scope) -> Unit) {
        when (this) {
            is LifecycleOwner -> ScopeObservable.getScope(this.hashCode(), osr)
            is View -> findScopeForView(this, osr)
            else -> throw IllegalArgumentException()
        }
    }

    private fun findScopeForView(view: View, osr: (Scope) -> Unit) {
        if (view.isAttachedToWindow) {
            findParentScopeForView(view).doScoped(osr)
        } else {
            view.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
                override fun onViewDetachedFromWindow(v: View?) {}
                override fun onViewAttachedToWindow(v: View?) {
                    findParentScopeForView(view).doScoped(osr)
                    view.removeOnAttachStateChangeListener(this)
                }
            })
        }
    }

    fun newScope() = Scope(UUID.randomUUID())

    fun findParentScopeForView(view: View): AutoScoped {

        val parentViewScope = view.findScopeInParentView()
        if (parentViewScope != null) return parentViewScope

        if (view.id == View.NO_ID) throw IllegalArgumentException("View must have an id $view")
        val activity = view.context as? AppCompatActivity
            ?: throw IllegalArgumentException("View does not have an AppCompatActivity $view")
        val fragmentManager = activity.supportFragmentManager

        val parentFragmentScope = fragmentManager.findScopeInFragmentClosestToTheView(view)
        if (parentFragmentScope != null) return parentFragmentScope

        if (activity is AutoScoped) return activity

        throw IllegalArgumentException("View does not have any parent scopes $view")
    }

    fun FragmentManager.findScopeInFragmentClosestToTheView(view: View): AutoScoped? {
        val scopedFragment = fragments.firstOrNull {
            val scopedChildFragment = it.childFragmentManager.findScopeInFragmentClosestToTheView(view)
            if (scopedChildFragment != null) return scopedChildFragment

            it.containsView(view) && it is AutoScoped
        } as? AutoScoped
        return scopedFragment
    }

    fun View.findScopeInParentView(): AutoScoped? {
        val parentView = parent as? View
        if (parentView != null) {
            return if (parentView is AutoScoped) parentView else parentView.findScopeInParentView()
        }
        return null
    }

    inline private fun Fragment.containsView(view: View) = view == this.view?.findViewById(view.id)
}

