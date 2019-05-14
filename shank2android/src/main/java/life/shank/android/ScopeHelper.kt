package life.shank.android

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import life.shank.Scope
import java.util.UUID

@Suppress("NOTHING_TO_INLINE")
internal object ScopeHelper {

    fun newScope() = Scope(UUID.randomUUID())

    fun findScopeForView(view: View, onScopeReady: (Scope) -> Unit) {
        if (view.isAttachedToWindow) {
            findParentScopeForView(view).onScopeReady(onScopeReady)
        } else {
            view.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
                override fun onViewDetachedFromWindow(v: View?) {}
                override fun onViewAttachedToWindow(v: View?) {
                    findParentScopeForView(view).onScopeReady(onScopeReady)
                    view.removeOnAttachStateChangeListener(this)
                }
            })
        }
    }

    private fun findParentScopeForView(view: View): AutoScoped {

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

    private fun FragmentManager.findScopeInFragmentClosestToTheView(view: View): AutoScoped? {
        return fragments.firstOrNull {
            val scopedChildFragment = it.childFragmentManager.findScopeInFragmentClosestToTheView(view)
            if (scopedChildFragment != null) return scopedChildFragment

            it.containsView(view) && it is AutoScoped
        } as? AutoScoped
    }

    private fun View.findScopeInParentView(): AutoScoped? {
        val parentView = parent as? View
        if (parentView != null) {
            return if (parentView is AutoScoped) parentView else parentView.findScopeInParentView()
        }
        return null
    }

    private inline fun Fragment.containsView(view: View) = view == this.view?.findViewById(view.id)
}
