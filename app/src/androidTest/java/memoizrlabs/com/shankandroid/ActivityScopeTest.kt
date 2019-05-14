package memoizrlabs.com.shankandroid

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.memoizr.assertk.expect
import memoizrlabs.com.shankandroid.testing.ActivityForTest
import memoizrlabs.com.shankandroid.testing.ViewForTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ActivityScopeTest {
    @JvmField
    @Rule
    val activityRule = ActivityTestRule(ActivityForTest::class.java, false, false)

    @Test
    fun getsScopeLazilyForActivity() {
        activityRule.launchActivity(Intent())

        val v1 = get<ActivityForTest>().value

        currentActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        val v2 = get<ActivityForTest>().value

        expect that v1 isEqualTo v2
    }

    @Test
    fun getsScopeLazilyForViewInActivity() {
        activityRule.launchActivity(Intent())

        val v1 = get<ActivityForTest>().view.value

        currentActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        val v2 = get<ActivityForTest>().view.value

        expect that v1 isEqualTo v2
    }

    @Test
    fun getsScopeLazilyForFragment() {
        activityRule.launchActivity(Intent())

        val v1 = get<ActivityForTest>().fragment.value

        currentActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        val v2 = get<ActivityForTest>().fragment.value

        expect that v1 isEqualTo v2
    }

    @Test
    fun getsScopeLazilyForViewInFragment() {
        activityRule.launchActivity(Intent())

        val v1 = (get<ActivityForTest>().fragment.view as ViewForTest).value

        currentActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        val v2 = (get<ActivityForTest>().fragment.view as ViewForTest).value

        expect that v1 isEqualTo v2
    }

    @Test
    fun fragmentViewScopeAndActivityViewScopeAreDifferent() {
        activityRule.launchActivity(Intent())

        val v1 = (get<ActivityForTest>().fragment.view as ViewForTest).value
        val v2 = get<ActivityForTest>().view.value

        expect that v1 isEqualTo v2
    }
}

