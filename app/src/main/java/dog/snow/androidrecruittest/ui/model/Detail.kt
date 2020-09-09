package dog.snow.androidrecruittest.ui.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Detail(
    var photoId: Int?=null,
    var photoTitle: String?=null,
    var albumTitle: String?=null,
    var albumId: Int?=null,
    var username: String?=null,
    var email: String?=null,
    var phone: String?=null,
    var url: String?=null
) : Parcelable
