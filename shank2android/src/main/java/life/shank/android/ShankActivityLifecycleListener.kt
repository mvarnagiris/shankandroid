package life.shank.android

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import life.shank.Scope
import java.util.*

@JvmField val activityScopedCache = IdentityHashMap<Activity, Scope>()
@JvmField val fragmentScopedCache = IdentityHashMap<Fragment, Scope>()
@JvmField val scopeMap = IdentityHashMap<Any, Scope>()

object ShankActivityLifecycleListener : Application.ActivityLifecycleCallbacks {
    internal val scopeKey = "shank_scope_key"

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        activityScopedCache[activity] = savedInstanceState?.getSerializable(scopeKey) as? Scope
                ?: activity.intent.getSerializableExtra(scopeKey) as? Scope
                ?: Scope(UUID.randomUUID())
        (activity as FragmentActivity)
                .supportFragmentManager
                .registerFragmentLifecycleCallbacks(ShankFragmentLifecycleListener, true)
    }

    override fun onActivityDestroyed(activity: Activity) {
        if (activity.isFinishing) {
            activityScopedCache[activity]?.clear()
        }
        activityScopedCache.remove(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle): Unit =
            outState.putSerializable(scopeKey, activityScopedCache[activity])

    override fun onActivityPaused(activity: Activity?) = Unit
    override fun onActivityResumed(activity: Activity?) = Unit
    override fun onActivityStarted(activity: Activity?) = Unit
    override fun onActivityStopped(activity: Activity?) = Unit
}

