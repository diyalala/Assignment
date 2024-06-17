package com.example.assignment.ViewModel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assignment.Model.UserModel
import com.example.assignment.Repository.UserRepository

class UserViewModel(val repository: UserRepository):ViewModel(){
    fun deleteData(id:String,callback:(Boolean,String?) -> Unit){
        repository.deleteData(id,callback)
    }
    fun deleteImage(imageName:String,callback:(Boolean,String?) -> Unit){
        repository.deleteImage(imageName,callback)
    }


    fun updateUser(id:String,data: MutableMap<String,Any>?,callback:(Boolean,String?) -> Unit){
        repository.UpdateUser(id,data,callback)
    }

    fun addUser(userModel: UserModel, callback: (Boolean, String?) -> Unit) {
        repository.addUser(userModel, callback)
    }

    fun uploadImage(imageName: String, imageUrl: Uri, callback: (Boolean, String?) -> Unit) {
        repository.uploadImage(imageName, imageUrl){success,imageUrl ->
            callback(success, imageUrl)
        }
    }

    private var _userList =MutableLiveData<List<UserModel>?>()
    var userList = MutableLiveData<List<UserModel>?>()
        get() = _userList

    var _loadingState = MutableLiveData<Boolean>()
    var loadingState = MutableLiveData<Boolean>()
        get() = _loadingState

    fun fetchUser() {
        _loadingState.value = true
        repository.getAllUser() { userList, success, message ->
            if (userList != null) {
                _loadingState.value= false
                _userList.value = userList
            }
        }
    }

}