package memoizrlabs.com.shankandroid

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.list_item.view.*
import life.shank.android.AutoAttachable
import life.shank.android.AutoScoped
import memoizrlabs.com.shankandroid.ListModule.listPresenter

class ListItemView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, style: Int = 0) :
    LinearLayout(context, attrs, style), ListPresenter.View, AutoAttachable, AutoScoped  {

    init {
        id = View.generateViewId()
    }

    var lastContent: Int? = null

    fun initialize(data: Int) {
        listPresenter.register(this, data)

        doScoped {
            println("scope ${it.value}")
        }
    }

    override fun setContent(data: Int) {
        text.text = data.toString()
        println("attaching lastContent: $lastContent, newContent: $data")
        lastContent = data
    }

    override fun doOnDetach() {
        println("detaching lastContent content: $lastContent")
    }
}

