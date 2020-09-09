package dog.snow.androidrecruittest.base


import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.RecyclerView
import dog.snow.androidrecruittest.callback.OnItemClickListener


abstract class BaseRecyclerViewAdapter<ListItem>:  RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: ArrayList<ListItem>? = ArrayList<ListItem>()
    protected lateinit var itemClickListener: OnItemClickListener

    fun addItems(items: ArrayList<ListItem>) {
        this.list?.addAll(items)
        reload()
    }

    fun clear() {
        this.list?.clear()
        reload()
    }

    fun getItem(position: Int): ListItem? {
        return this.list?.get(position)
    }

    fun setOnItemClickListener(onItemClickListener:
                               OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    override fun getItemCount(): Int = list!!.size

    private fun reload() {
        Handler(Looper.getMainLooper()).post { notifyDataSetChanged() }
    }
}
