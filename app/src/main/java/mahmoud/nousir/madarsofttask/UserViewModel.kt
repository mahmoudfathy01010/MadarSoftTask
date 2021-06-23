package mahmoud.nousir.madarsofttask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mahmoud.nousir.madarsofttask.db.Dao
import mahmoud.nousir.madarsofttask.model.Gender
import mahmoud.nousir.madarsofttask.model.User

class UserViewModel(private val dataBase: Dao) : ViewModel() {
    private var user: User =
        User()
    private val _isVerified = MutableLiveData<Boolean>()
    val isVerified:LiveData<Boolean> =_isVerified
    private val _isSaved = MutableLiveData<Boolean>()
    val isSaved:LiveData<Boolean> =_isSaved

    lateinit var userLiveData:LiveData<User>

    fun setUserName(userName: String){
        user.userName=userName
        checkData()
    }

    fun setAge(age: String){
        user.age=age
        checkData()
    }

    fun setJobTitle(jobTitle: String){
        user.jobTile=jobTitle
        checkData()
    }

    fun setGender(gender: Gender){
        user.gender=gender
        checkData()
    }

    private fun checkData(){
        _isVerified.value = user.userName!=""&&user.age!=""&&user.jobTile!=""
    }

    fun saveData() {
        viewModelScope.launch(Dispatchers.IO) {
           dataBase.insertData(user)
            withContext(Dispatchers.Main){
                _isSaved.value=true
            }
        }
    }

    fun getUserFromDataBase() {
        userLiveData = dataBase.getData()
    }

    fun navigationIsDone() {
        _isSaved.value=false
        _isVerified.value=false
        user = User()

    }

    fun getUser():User{
        return user
    }
}