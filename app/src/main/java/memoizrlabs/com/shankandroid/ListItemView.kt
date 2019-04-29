package memoizrlabs.com.shankandroid

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.list_item.view.*
import memoizrlabs.com.shankandroid.ListModule.listPresenter
import memoizrlabs.com.shankandroid.ListModule.otherListPresenter
import life.shank.android.Helper.scopee
import memoizrlabs.com.shankandroid.autoattachandroid.AutoAttached
import kotlin.system.measureNanoTime

class ListItemView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, style: Int = 0)
    : LinearLayout(context, attrs, style), ListPresenter.View, OtherListPresenter.View, AutoAttached {
    override fun somethingFromOtherPresenter() {
        println("doing something from other presenter")
    }

    fun initialize(data: Int) {
        println(measureNanoTime {
            listPresenter.bind(scopee(), this, data)
            listPresenter.p
        })

        otherListPresenter.bind(scopee(),this, data)
    }

    override fun setContent(data: Int) {
        println("setting content")
        text.text = data.toString()
    }
}

