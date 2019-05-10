package life.shank.android

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import life.shank.Scope
import java.util.*

object ShankFragmentLifecycleListener : FragmentManager.FragmentLifecycleCallbacks() {
    private val fragmentScopeKey = "shank_fragment_scope_key"
    override fun onFragmentPreCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        fragmentScopedCache[f] = savedInstanceState?.getSerializable(fragmentScopeKey) as? Scope
            ?: Scope(UUID.randomUUID())
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        if (f.activity?.isFinishing == true || f.isRemoving) {
            fragmentScopedCache[f]?.clear()
        }
        fragmentScopedCache.remove(f)
    }

    override fun onFragmentSaveInstanceState(fm: FragmentManager, f: Fragment, outState: Bundle): Unit =
        outState.putSerializable(fragmentScopeKey, fragmentScopedCache[f])
}