package dog.snow.androidrecruittest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dog.snow.androidrecruittest.ui.DetailsFragment
import dog.snow.androidrecruittest.ui.ListFragment
import dog.snow.androidrecruittest.ui.model.ListItem
import kotlinx.coroutines.*
import org.json.JSONException
import java.net.URL

class SplashActivity: AppCompatActivity(R.layout.splash_activity) {
    private fun showError(errorMessage: String?) {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.cant_download_dialog_title)
            .setMessage(getString(R.string.cant_download_dialog_message, errorMessage))
            .setPositiveButton(R.string.cant_download_dialog_btn_positive) { _, _ -> /*tryAgain()*/ }
            .setNegativeButton(R.string.cant_download_dialog_btn_negative) { _, _ -> finish() }
            .create()
            .apply { setCanceledOnTouchOutside(false) }
            .show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    //    showError("first try!")

        val arrayList = ArrayList<ListItem>()
        val mainIntent = Intent(this, MainActivity::class.java)
        val job2: Job = CoroutineScope(Dispatchers.IO).launch {
            try {
                //supportFragmentManager.beginTransaction().replace(R.id.container, ListFragment.newInstance(), ListFragment.TAG).commit();
                //supportFragmentManager.beginTransaction().replace(R.id.containerDetails, DetailsFragment.newInstance(), DetailsFragment.TAG).commit();
                //ListFragment.newInstance()
                //DetailsFragment.newInstance()
                Log.d("HTTP_JSON", "All photos have been downloaded")
            } catch (je: JSONException) {
                Log.e("JSON_ERROR", je.message)
            }
        }
        job2.start()
        if (!job2.isCompleted) {
            runBlocking {
                delay(2000L)
            }
        }
        val job: Job = CoroutineScope(Dispatchers.IO).launch  {
            //Log.d("HTTP_JSON", RawPhotoStream().getJSONArray().toString())
            //arrayList.addAll(RawPhotoStream().getJSONArray())
            delay(1000L)
            startActivity(mainIntent)
            delay(2000L)
            finish()
        }
        job.start()

        if(job.isCompleted){
            job.cancel()
        }
    }
}
