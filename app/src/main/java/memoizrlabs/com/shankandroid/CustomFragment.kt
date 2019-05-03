package memoizrlabs.com.shankandroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import life.shank.android.AutoAttachable
import life.shank.android.AutoScoped
import memoizrlabs.com.shankandroid.ListModule.listPresenter

class CustomFragment : Fragment(), AutoScoped, AutoAttachable, ListPresenter.View {
    override fun doOnDetach() {
        println("detaching fragment, goodbye")
    }

    override fun setContent(data: Int) {
        println("setting content in Fragment from presenter with value $data")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        listPresenter.register(this, 99)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragmentcontent, container)
    }
}