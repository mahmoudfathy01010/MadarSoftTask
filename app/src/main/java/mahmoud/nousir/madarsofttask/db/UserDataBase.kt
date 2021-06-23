package mahmoud.nousir.madarsofttask.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import mahmoud.nousir.madarsofttask.model.User


@Database(entities = [User::class],version = 1,exportSchema = false)
abstract class UserDataBase : RoomDatabase() {
    abstract val dao:Dao
    companion object{
        @Volatile
        private var INSTANCE:UserDataBase?=null
        fun getInstance(context: Context):UserDataBase{
            synchronized(this){
                var instance = INSTANCE
                if(instance==null){
                    instance= Room.databaseBuilder(
                        context.applicationContext,
                        UserDataBase::class.java,
                        USER_DATABASE_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE =instance
                }
                return instance
            }
        }
    }
}

const val USER_DATABASE_NAME = "user"
