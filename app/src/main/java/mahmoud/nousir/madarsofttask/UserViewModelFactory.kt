package mahmoud.nousir.madarsofttask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mahmoud.nousir.madarsofttask.db.Dao

@Suppress("UNCHECKED_CAST")
class UserViewModelFactory(private val dataBase:Dao):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)){
            return UserViewModel(dataBase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}