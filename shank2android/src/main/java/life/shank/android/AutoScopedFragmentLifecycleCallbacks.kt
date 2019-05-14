package life.shank.android

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import life.shank.Scope
import life.shank.android.ScopeHelper.newScope

object AutoScopedFragmentLifecycleCallbacks : FragmentManager.FragmentLifecycleCallbacks() {

    private const val fragmentScopeKey = "shank_fragment_scope_key"

    override fun onFragmentPreCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        if (f !is AutoScoped) return

        ScopeObservable.putScope(
            f.hashCode(),
            savedInstanceState?.getSerializable(fragmentScopeKey) as? Scope ?: newScope()
        )
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        if (f !is AutoScoped) return

        if (f.activity?.isFinishing == true || f.isRemoving) {
            ScopeObservable.getScope(f.hashCode()) {
                it.clear()
            }
        }
        ScopeObservable.clear(f.hashCode())
    }

    override fun onFragmentSaveInstanceState(fm: FragmentManager, f: Fragment, outState: Bundle) {
        if (f !is AutoScoped) return

        ScopeObservable.getScope(f.hashCode()) {
            outState.putSerializable(fragmentScopeKey, it)
        }
    }
}