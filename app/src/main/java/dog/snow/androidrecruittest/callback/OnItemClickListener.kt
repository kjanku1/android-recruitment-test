package dog.snow.androidrecruittest.callback

import android.view.View

interface OnItemClickListener {
    abstract fun onItemClick(position: Int, view: View?)
}
