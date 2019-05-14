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

        ObservableLifecycleOwnerScope.putScope(
            f,
            savedInstanceState?.getSerializable(fragmentScopeKey) as? Scope ?: newScope()
        )
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        if (f !is AutoScoped) return

        if (f.activity?.isFinishing == true || f.isRemoving) {
            ObservableLifecycleOwnerScope.doOnScopeReady(f) {
                it.clear()
            }
        }
        ObservableLifecycleOwnerScope.clear(f)
    }

    override fun onFragmentSaveInstanceState(fm: FragmentManager, f: Fragment, outState: Bundle) {
        if (f !is AutoScoped) return

        ObservableLifecycleOwnerScope.doOnScopeReady(f) {
            outState.putSerializable(fragmentScopeKey, it)
        }
    }
}