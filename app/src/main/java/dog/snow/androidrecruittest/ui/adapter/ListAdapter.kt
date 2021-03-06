package dog.snow.androidrecruittest.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.base.BaseRecyclerViewAdapter
import dog.snow.androidrecruittest.ui.model.ListItem
import kotlinx.android.synthetic.main.list_item.view.*

class ListAdapter: BaseRecyclerViewAdapter<ListItem>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item,
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var myHolder = holder as MyViewHolder
        getItem(position)?.let { myHolder.setUpView(model = it) }
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private val textView1: TextView = view.tv_album_title
        private val textView2: ImageView = view.iv_id
        private val textView3: TextView = view.tv_title
        private val textView4: TextView = view.tv_url
        private val textView5: TextView = view.tv_thumbUrl
        init {
            view.setOnClickListener(this)
        }

        fun setUpView(model: ListItem) {

            itemView.tv_title.text = model.title
            itemView.tv_album_title.text = model.albumId.toString()
            itemView.tv_url.text = model.url
            itemView.tv_thumbUrl.text = model.thumbnailUrl
            textView1.text = model?.albumId.toString()
            textView3.text = model?.title
            textView4.text = model?.url
            textView5.text = model?.thumbnailUrl
            if (adapterPosition != null) {
                Picasso.get()
                    .load(model.thumbnailUrl)
                    .centerCrop()
                    .fit()
                    .into(textView2)
            }
        }

        override fun onClick(v: View?) {
            itemClickListener.onItemClick(adapterPosition, v)
        }
    }
}
