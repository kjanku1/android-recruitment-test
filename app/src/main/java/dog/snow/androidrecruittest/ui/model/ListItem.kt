package dog.snow.androidrecruittest.ui.model

import android.graphics.drawable.Drawable
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListItem(
    var albumId: Int?=null,
    var id: Int?=null,
    var title: String?=null,
    var url: String?=null,
    var thumbnailUrl: String?=null
) : Parcelable
