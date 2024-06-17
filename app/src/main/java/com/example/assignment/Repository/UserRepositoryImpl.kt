package com.example.assignment.Repository

import android.net.Uri
import com.example.assignment.Model.UserModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class UserRepositoryImpl : UserRepository{
    var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    var ref = firebaseDatabase.reference.child("user")

    var firebaseStorage:FirebaseStorage = FirebaseStorage.getInstance()
    var storageref = firebaseStorage.reference.child("user")
    override fun uploadImage(
        imageName: String,
        imageUrl: Uri,
        callback: (Boolean, String?) -> Unit
    ) {
        var imageRefernce= storageref.child(imageName)

        imageUrl?.let {url->
            imageRefernce.putFile(url).addOnSuccessListener{
                imageRefernce.downloadUrl.addOnSuccessListener {downloadUrl->
                    var imageUrl = downloadUrl.toString()
                    callback(true,imageUrl)
                }

            }.addOnFailureListener {
                callback(false,"")

            }
        }

    }

    override fun addUser(userModel: UserModel, callback: (Boolean, String?) -> Unit) {
        var id = ref.push().key.toString()

        userModel.id = id

        ref.child(id).setValue(userModel).addOnCompleteListener {
            if (it.isSuccessful){
                callback(true,"Data uploaded successfully")
            }else{
                callback(false, "unable to upload data")
            }

        }
    }

    override fun getAllUser(callback: (List<UserModel>?, Boolean, String?) -> Unit) {
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var userList = mutableListOf<UserModel>()
                for (eachData in snapshot.children) {
                    var user = eachData.getValue(UserModel::class.java)
                    if (user != null) {

                        userList.add(user)
                    }
                }
                callback(userList, true, "Data successfully retrived")
            }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun UpdateUser(
        id: String,
        data: MutableMap<String, Any>?,
        callback: (Boolean, String?) -> Unit
    ) {
        data?.let {
            ref.child(id).updateChildren(it).addOnCompleteListener {
                if (it.isSuccessful){
                    callback(true,"Your data has been updated")
                }else{
                    callback(false,"Unable to Update data")
                }

            }
        }
    }

    override fun deleteData(id: String, callback: (Boolean, String?) -> Unit) {
        ref.child(id).removeValue().addOnCompleteListener {
            if (it.isSuccessful){
                callback(true,"Data has been deleted")

            }else{
                callback(false,"Unable to delete image")
            }

        }
    }

    override fun deleteImage(imageName: String, callback: (Boolean, String?) -> Unit) {
        storageref.child("user").child(imageName).delete()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    callback(true,"Image Deleted")
                }else{
                    callback(false,"Unable to delete image")
                }
            }
    }


}