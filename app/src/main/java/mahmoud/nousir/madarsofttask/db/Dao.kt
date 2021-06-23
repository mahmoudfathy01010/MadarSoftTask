package mahmoud.nousir.madarsofttask.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mahmoud.nousir.madarsofttask.model.User

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(user: User)

    @Query("select * from user")
    fun getData(): LiveData<User>
}