package life.shank.android

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import life.shank.Scope
import life.shank.android.ScopeHelper.newScope
import java.util.IdentityHashMap

internal val activityAutoScopeCache = IdentityHashMap<Activity, Scope>()

object AutoScopedActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {

    private const val activityScopeKey = "shank_activity_scope_key"

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (activity !is AutoScoped) return

        activityAutoScopeCache[activity] = savedInstanceState?.getSerializable(activityScopeKey) as? Scope ?: newScope()

        (activity as? FragmentActivity)
            ?.supportFragmentManager
            ?.registerFragmentLifecycleCallbacks(AutoScopedFragmentLifecycleCallbacks, true)
    }

    override fun onActivityDestroyed(activity: Activity) {
        if (activity !is AutoScoped) return

        if (activity.isFinishing) {
            activityAutoScopeCache[activity]?.clear()
        }
        activityAutoScopeCache.remove(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        if (activity !is AutoScoped) return

        outState.putSerializable(activityScopeKey, activityAutoScopeCache[activity])
    }

    override fun onActivityPaused(activity: Activity?) = Unit
    override fun onActivityResumed(activity: Activity?) = Unit
    override fun onActivityStarted(activity: Activity?) = Unit
    override fun onActivityStopped(activity: Activity?) = Unit
}

