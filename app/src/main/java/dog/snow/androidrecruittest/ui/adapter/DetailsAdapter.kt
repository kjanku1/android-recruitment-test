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
import dog.snow.androidrecruittest.ui.model.Detail
import kotlinx.android.synthetic.main.details_item.view.*
import kotlinx.android.synthetic.main.list_item.view.tv_album_title

class DetailsAdapter: BaseRecyclerViewAdapter<Detail>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.details_item,
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

        private val imageView: ImageView = view.iv_photo
        private val textView3: TextView = view.tv_photo_title
        private val textView1: TextView = view.tv_album_title
        private val textView2: TextView = view.tv_username
        private val textView4: TextView = view.tv_email
        private val textView5: TextView = view.tv_phone
        init {
            view.setOnClickListener(this)
        }

        fun setUpView(model: Detail) {

            if (adapterPosition != null) {
                Picasso.get()
                    .load(model.url)
                    .centerCrop()
                    .fit()
                    .into(imageView)
            }
            itemView.tv_photo_title.text = model.photoTitle
            itemView.tv_album_title.text = model.albumTitle
            itemView.tv_username.text = model.username
            itemView.tv_email.text = model.email
            itemView.tv_phone.text = model.phone

            textView1.text = model?.albumTitle
            textView2.text = model?.username
            textView3.text = model?.photoTitle
            textView4.text = model?.email
            textView5.text = model?.phone

        }

        override fun onClick(v: View?) {
            itemClickListener.onItemClick(adapterPosition, v)
        }
    }
}
