package memoizrlabs.com.shankandroid

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.Holder>() {
    private val items = (1..100).toList()

    override fun getItemCount(): Int = items.size
    override fun onBindViewHolder(holder: Holder, position: Int) = holder.view.initialize(items[position])
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
            Holder(View.inflate(parent.context, R.layout.list_item, null) as ListItemView)

    data class Holder(val view: ListItemView) : RecyclerView.ViewHolder(view)
}