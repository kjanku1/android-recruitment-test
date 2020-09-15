package dog.snow.androidrecruittest.database

import androidx.lifecycle.LiveData
import androidx.room.*
import dog.snow.androidrecruittest.repository.model.RawPhoto

@Dao
interface PhotosDao {

    @Insert
    fun insert(photo: Photo)

    @Update
    fun update(photo: Photo)

    @Delete
    fun delete(photo: Photo)

    @Query("SELECT * FROM photos_table")
    fun getAllPhotos(): LiveData<List<Photo>>

    @Query("DELETE FROM photos_table")
    fun deleteAllRows()

}
