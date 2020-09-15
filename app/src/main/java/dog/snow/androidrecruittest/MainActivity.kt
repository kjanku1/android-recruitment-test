package dog.snow.androidrecruittest

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import dog.snow.androidrecruittest.ui.DetailsFragment
import dog.snow.androidrecruittest.ui.ListFragment
import dog.snow.androidrecruittest.ui.adapter.ListAdapter
import dog.snow.androidrecruittest.ui.model.Detail
import dog.snow.androidrecruittest.ui.model.ListItem


class MainActivity : AppCompatActivity(), ListFragment.OnListInteractionListener,DetailsFragment.DetailsInteractionListener{
    private val TAG : String = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(findViewById(R.id.toolbar))
        setContentView(R.layout.activity_main_)

        //supportFragmentManager.beginTransaction().replace(R.id.container, ListFragment.newInstance(), ListFragment.TAG).commit();
        //supportFragmentManager.beginTransaction().replace(R.id.containerDetails, DetailsFragment.newInstance(), DetailsFragment.TAG).commit();
    }

    fun loadDetails(item: Int) {
        //var pos = adapter.getItem(item)
        var detailFrag = DetailsFragment(item)
        supportFragmentManager.beginTransaction().replace(R.id.container, detailFrag).addToBackStack(detailFrag.toString()).commit()
        //supportFragmentManager.beginTransaction().replace(R.id.containerDetails, DetailsFragment.newInstance(), DetailsFragment.TAG).commit();
    }

    override fun onDetailInteraction(uri: Uri) {
        TODO("Not yet implemented")
    }

    override fun onListInteraction(uri: Uri) {
        TODO("Not yet implemented")
    }
}

