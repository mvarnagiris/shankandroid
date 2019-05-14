package memoizrlabs.com.shankandroid.testing


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import life.shank.ShankModule
import life.shank.android.AutoScoped
import life.shank.android.supportAutoAttach
import life.shank.scoped
import memoizrlabs.com.shankandroid.PresenterAdapter
import memoizrlabs.com.shankandroid.testing.TestModule.scopedResourceForActivity
import memoizrlabs.com.shankandroid.testing.TestModule.scopedResourceForFragment
import memoizrlabs.com.shankandroid.testing.TestModule.scopedResourceForViewInActivity
import java.util.UUID

class ActivityForTest : AppCompatActivity(), AutoScoped {

    lateinit var value: UUID
    lateinit var fragment: FragmentForTest
    lateinit var view: ViewForTest

    init {
        scopedResourceForActivity { value = it }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ViewForTest(this)
        setContentView(view)

        fragment = supportFragmentManager.findFragmentByTag("yo") as? FragmentForTest?
            ?: FragmentForTest().also {
                supportFragmentManager.beginTransaction().apply {
                    add(view.id, it, "yo")
                    commit()
                }
            }
    }
}

@SuppressLint("ResourceType")
class ViewForTest(context: Context) : FrameLayout(context), AutoScoped {
    lateinit var value: UUID

    init {
        id = 666
        scopedResourceForViewInActivity { value = it }
    }
}

object TestModule : ShankModule {
    val scopedResourceForActivity = scoped { -> UUID.randomUUID() }
    val scopedResourceForFragment = scoped { -> UUID.randomUUID() }
    val scopedResourceForViewInActivity = scoped { -> UUID.randomUUID() }

    val activityPresenter = scoped { -> PresenterForTest() }.supportAutoAttach()
}

class FragmentForTest : Fragment(), AutoScoped {

    lateinit var value: UUID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        scopedResourceForFragment { value = it }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ViewForTest(context!!)
    }
}


class PresenterForTest : PresenterAdapter<PresenterForTest.View>() {
    interface View : PresenterAdapter.View
}
