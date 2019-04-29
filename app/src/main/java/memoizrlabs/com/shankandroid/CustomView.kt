package memoizrlabs.com.shankandroid

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragmentcontent.view.*
import life.shank.android.AutoScoped


class CustomView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, style: Int = 0) : LinearLayout(context, attrs, style), AutoScoped {

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = RecyclerAdapter()
    }
}