package memoizrlabs.com.shankandroid

import android.R
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.test.espresso.Espresso
import androidx.test.espresso.matcher.ViewMatchers


fun Context.toActivity(): Activity = when (this) {
    is Activity -> this
    is android.view.ContextThemeWrapper -> this.baseContext.toActivity()
    is ContextWrapper -> this.baseContext.toActivity()
    else -> throw IllegalArgumentException("Context $this is not an Activity.")
}

val currentActivity : Activity
    get() {
        var activity: Activity? = null
        Espresso.onView(ViewMatchers.withId(R.id.content)).check { view, noViewFoundException -> activity = view.context.toActivity() }
        return activity!!
    }

fun inActivity(block: Activity.() -> Unit) {
    block(currentActivity)
}

fun <T: Activity> get() = currentActivity as T
