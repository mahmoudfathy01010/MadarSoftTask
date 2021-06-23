package mahmoud.nousir.madarsofttask.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User (
    @PrimaryKey
    var id:Int?=1,
    var age:String?="",
    var userName:String?="",
    var jobTile: String?="",
    var gender: Gender?= Gender.MALE
)

enum class Gender{
    MALE,
    FEMALE
}