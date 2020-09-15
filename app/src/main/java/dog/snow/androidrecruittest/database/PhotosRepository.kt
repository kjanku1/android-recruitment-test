package dog.snow.androidrecruittest.database

import android.app.Application
import android.app.LauncherActivity
import androidx.lifecycle.LiveData
import dog.snow.androidrecruittest.database.Photo
import kotlinx.coroutines.*

class PhotosRepository (application: Application) {

    private var photosDao: PhotosDao

    init {
        val database: PhotosDatabase? = PhotosDatabase
            .getInstance(application.applicationContext)

        photosDao = database!!.photosDao()
    }

    fun insertPhoto(photo: Photo):Job=
        CoroutineScope(Dispatchers.IO).launch {
            photosDao.insert(photo)
        }
    fun updatePhoto(photo: Photo):Job=
        CoroutineScope(Dispatchers.IO).launch {
            photosDao.update(photo)
        }
    fun deletePhoto(photo: Photo):Job=
        CoroutineScope(Dispatchers.IO).launch {
            photosDao.delete(photo)
        }
    fun getAllPhotosAsync(): Deferred<LiveData<List<Photo>>> =
        CoroutineScope(Dispatchers.IO).async {
            photosDao.getAllPhotos()
        }
    fun deleteAllRows(photo: Photo):Job=
        CoroutineScope(Dispatchers.IO).launch {
            photosDao.deleteAllRows()
        }
}
