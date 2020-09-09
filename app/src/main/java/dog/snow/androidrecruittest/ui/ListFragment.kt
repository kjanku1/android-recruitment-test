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
import dog.snow.androidrecruittest.MainActivity
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.callback.OnItemClickListener
import dog.snow.androidrecruittest.ui.adapter.ListAdapter
import dog.snow.androidrecruittest.ui.model.Detail
import dog.snow.androidrecruittest.ui.model.ListItem
import kotlinx.android.synthetic.main.list_item.view.*
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.net.URL

class ListFragment: Fragment() {
    protected lateinit var rootView: View
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: ListAdapter

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateComponent()
    }

    private fun onCreateComponent() {
        adapter = ListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.list_fragment2, container, false);
        initView()
        return rootView
    }
    interface OnListInteractionListener {
        // TODO: Update argument type and name
        fun onListInteraction(uri: Uri)
    }
    private fun initView(){
        setUpAdapter()
        initializeRecyclerView()
        val jsonString:String = runBlocking(Dispatchers.IO){ readJsonFromWebString() }
        val parsedJsonList : ArrayList<ListItem> =  parseJsonStringToList(jsonString)
        adapter.addItems(parsedJsonList)
    }
    private fun setUpAdapter() {
        adapter.setOnItemClickListener(onItemClickListener = object :
            OnItemClickListener {
            override fun onItemClick(position: Int, view: View?) {
                var item = adapter.getItem(position)
                var pos = position
                var mainActivity : MainActivity = activity as MainActivity
                mainActivity.loadDetails(pos)
            }

        })
    }
    private fun initializeRecyclerView() {
        recyclerView = rootView.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }

    private suspend fun readJsonFromWebString() : String {
        var inputString = ""

        val job: Job = CoroutineScope(Dispatchers.IO).launch {
            try {
                val apiResponse =
                    URL("https://jsonplaceholder.typicode.com/photos?_limit=100").readText()
                inputString = apiResponse
                Log.d("HTTP_JSON", inputString)
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
    private fun parseJsonStringToList(jsonString: String): ArrayList<ListItem> {
        val arrayList :ArrayList<ListItem> = ArrayList<ListItem>(0)
        val listArray = JSONArray(jsonString)
        var i = 0
        var numIterations = listArray.length()
        while(i < numIterations){
            val listObject: JSONObject = listArray.getJSONObject(i)
            val item = ListItem()
            item.albumId = listObject.getInt("albumId")
            item.id = listObject.getInt("id")
            item.title = listObject.getString("title")
            item.url = listObject.getString("url")
            item.thumbnailUrl = listObject.getString("thumbnailUrl")
            arrayList.add(item)
            i++
        }
       // var result: ArrayList<Detail> = arrayList.filterTo(ArrayList<Detail>(),{ s -> s.title == "" })//filterTo(ArrayList<Detail>(),{ s -> s.title == "" })

        return arrayList//result
    }
}
