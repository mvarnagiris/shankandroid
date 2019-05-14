package life.shank.android

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import life.shank.Scope
import life.shank.android.ScopeHelper.newScope
import java.util.IdentityHashMap

internal val fragmentAutoScopeCache = IdentityHashMap<Fragment, Scope>()

internal object AutoScopedFragmentLifecycleCallbacks : FragmentManager.FragmentLifecycleCallbacks() {

    private const val fragmentScopeKey = "shank_fragment_scope_key"

    override fun onFragmentPreCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        if (f !is AutoScoped) return

        fragmentAutoScopeCache[f] = savedInstanceState?.getSerializable(fragmentScopeKey) as? Scope ?: newScope()
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        if (f !is AutoScoped) return

        if (f.activity?.isFinishing == true || f.isRemoving) {
            fragmentAutoScopeCache[f]?.clear()
        }
        fragmentAutoScopeCache.remove(f)
    }

    override fun onFragmentSaveInstanceState(fm: FragmentManager, f: Fragment, outState: Bundle) {
        if (f !is AutoScoped) return

        outState.putSerializable(fragmentScopeKey, fragmentAutoScopeCache[f])
    }
}