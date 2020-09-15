package dog.snow.androidrecruittest.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dog.snow.androidrecruittest.repository.model.RawPhoto

@Database(entities = [Photo::class], version = 1, exportSchema = false)
abstract class PhotosDatabase: RoomDatabase() {

    abstract fun photosDao(): PhotosDao

    companion object {
        private var instance: PhotosDatabase? = null
        fun getInstance(context: Context): PhotosDatabase?{
            if(instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    PhotosDatabase::class.java,
                    "photos_table")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }

        fun deleteInstanceOfDatabase(){
            instance = null
        }
    }
}

