package memoizrlabs.com.shankandroid

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import androidx.test.ext.junit.runners.AndroidJUnit4
import life.shank.android.AppContextModule.appContext
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun useAppContext() {
        val a = startActivity<FirstActivity>()
//        println(a.activityInfo.name)

    }

    private inline fun <reified T: Activity> startActivity(): ResolveInfo? {
        val intent = Intent(appContext(), T::class.java).apply {
            this.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        appContext().startActivity(intent)
        val a = appContext().packageManager.resolveActivity(intent, PackageManager.MATCH_ALL)

        return a
    }
}

