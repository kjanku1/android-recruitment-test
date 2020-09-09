package dog.snow.androidrecruittest.repository.model

import android.os.Parcelable
import android.util.Log
import dog.snow.androidrecruittest.ui.model.ListItem
import kotlinx.android.parcel.Parcelize
import org.json.JSONArray
import org.json.JSONException
import java.net.URL

@Parcelize
data class RawPhoto(
    val id: Int,
    val albumId: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
) : Parcelable

class RawPhotoStream {


    fun getJSONArray(): ArrayList<ListItem> {
        var jsonString = "OOPS SOMETHING WENT WRONG"
        val list = ArrayList<ListItem>()

        try {
           val apiResponse = URL("https://jsonplaceholder.typicode.com/photos?_limit=100").readText()
            jsonString = apiResponse

            val jsonArray=JSONArray(jsonString)
            //val list=ArrayList<ListItem>()
            var i = 0
            while(i<jsonArray.length()){
                val jsonObject=jsonArray.getJSONObject(i)
                list.add(
                    ListItem(
                        jsonObject.getInt("thumbnailUrl")
                    )
                )
                i++
            }
        } catch (je: JSONException) {
            Log.e("JSON_ERROR", je.message)
        }
        //return jsonString
        return list
    }
}
