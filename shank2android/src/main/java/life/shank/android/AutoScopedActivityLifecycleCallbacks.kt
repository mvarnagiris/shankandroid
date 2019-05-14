package life.shank.android

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import life.shank.Scope
import life.shank.android.ScopeHelper.newScope


object AutoScopedActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {

    private const val activityScopeKey = "shank_activity_scope_key"

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (activity is AutoScoped) {
            ScopeObservable.putScope(
                activity.hashCode(),
                savedInstanceState?.getSerializable(activityScopeKey) as? Scope ?: newScope()
            )
        }

        (activity as? FragmentActivity)
            ?.supportFragmentManager
            ?.registerFragmentLifecycleCallbacks(AutoScopedFragmentLifecycleCallbacks, true)
    }

    override fun onActivityDestroyed(activity: Activity) {
        if (activity !is AutoScoped) return

        if (activity.isFinishing) {
            ScopeObservable.getScope(activity.hashCode()) {
                it.clear()
            }
        }
        ScopeObservable.clear(activity.hashCode())
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        if (activity !is AutoScoped) return

        ScopeObservable.getScope(activity.hashCode()) {
            outState.putSerializable(activityScopeKey, it)
        }
    }

    override fun onActivityPaused(activity: Activity?) = Unit
    override fun onActivityResumed(activity: Activity?) = Unit
    override fun onActivityStarted(activity: Activity?) = Unit
    override fun onActivityStopped(activity: Activity?) = Unit
}

