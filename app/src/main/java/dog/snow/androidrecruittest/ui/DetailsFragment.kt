package dog.snow.androidrecruittest.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.callback.OnItemClickListener
import dog.snow.androidrecruittest.ui.adapter.DetailsAdapter
import dog.snow.androidrecruittest.ui.model.Detail
import dog.snow.androidrecruittest.ui.model.ListItem
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.net.URL

class DetailsFragment(item: Int) : Fragment(){
    protected lateinit var rootView: View
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: DetailsAdapter
    val pos = item
    companion object {
        var TAG = ListFragment::class.java.simpleName
        const val ARG_POSITION: String = "positioin"

        fun newInstance(): ListFragment {
            var fragment = ListFragment();
            val args = Bundle()
            args.putInt(ARG_POSITION, 1)
            fragment.arguments = args
            return fragment
        }
    }
    interface DetailsInteractionListener {
        // TODO: Update argument type and name
        fun onDetailInteraction(uri: Uri)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateComponent()
    }

    private fun onCreateComponent() {
        adapter = DetailsAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.list_fragment2, container, false);
        initView()
        return rootView
    }

    private fun initView(){
        setUpAdapter()
        initializeRecyclerView()
        val jsonString:DownloadedData = runBlocking(Dispatchers.IO){ readJsonFromWebString(pos) }
        val parsedJsonList : ArrayList<Detail> =  parseJsonStringToList(jsonString)
        adapter.addItems(parsedJsonList)
    }

    private fun setUpAdapter() {
        adapter.setOnItemClickListener(onItemClickListener = object :
            OnItemClickListener {
            override fun onItemClick(position: Int, view: View?) {
                var item = adapter.getItem(position)
            }

        })
    }
    private fun initializeRecyclerView() {
        recyclerView = rootView.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }

    private suspend fun readJsonFromWebString(pos: Int) : DownloadedData {

        var inputStringPhoto = ""
        var inputStringAlbum = ""
        var inputStringUsers = ""
        var inputString = DownloadedData("","","")

        val job: Job = CoroutineScope(Dispatchers.IO).launch {
            try {
                val apiResponsePhoto =
                    URL("https://jsonplaceholder.typicode.com/photos?_limit=100").readText()
                inputStringPhoto = apiResponsePhoto
                val apiResponseAlbum =
                    URL("https://jsonplaceholder.typicode.com/albums").readText()
                    inputStringAlbum = apiResponseAlbum
                val apiResponseUsers =
                    URL("https://jsonplaceholder.typicode.com/users").readText()
                inputStringUsers = apiResponseUsers
                inputString = DownloadedData(inputStringPhoto,inputStringAlbum,inputStringUsers)
                Log.d("HTTP_JSON", inputStringPhoto)
            } catch (je: JSONException) {
                Log.e("JSON_ERROR", je.message)
            }
        }
        job.start()
        if (!job.isCompleted) {
            runBlocking {
                delay(1000L)
            }
        }
        return inputString
    }
    private fun parseJsonStringToList(jsonString: DownloadedData): ArrayList<Detail> {
        val arrayList :ArrayList<Detail> = ArrayList<Detail>(0)
        val listArray = JSONArray(jsonString.photos)
        val listArrayAlbum = JSONArray(jsonString.albums)
        val listArrayUser = JSONArray(jsonString.users)
        var i = pos
        var numIterations = listArray.length()
        while(i == pos/*< numIterations*/){
            val listObject: JSONObject = listArray.getJSONObject(i)
            val albumObject: JSONObject = listArrayAlbum.getJSONObject(listObject.getInt("albumId"))
            val userObject: JSONObject = listArrayUser.getJSONObject(albumObject.getInt("userId"))
            val item = Detail()
            item.photoId = listObject.getInt("id")
            item.albumTitle = albumObject.getString("title")
            item.photoTitle = listObject.getString("title")
            item.url = listObject.getString("url")
            item.username = userObject.getString("username")
            item.phone = userObject.getString("phone")
            item.email = userObject.getString("email")
            arrayList.add(item)
            i++
        }
        return arrayList
    }
}
