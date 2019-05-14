package life.shank.android

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import life.shank.Scope
import java.util.UUID

object AutoScopedFragmentLifecycleCallbacks : FragmentManager.FragmentLifecycleCallbacks() {

    private const val fragmentScopeKey = "shank_fragment_scope_key"

    override fun onFragmentPreCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        if (f !is AutoScoped) return


        ObservableLifecycleOwnerScope.putScope(
            f,
            f.arguments?.keySet()?.find { f.arguments?.getSerializable(it) is Scope }?.let { (it as Scope).nest() } ?:
            savedInstanceState?.getSerializable(fragmentScopeKey) as? Scope ?: Scope(UUID.randomUUID())
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