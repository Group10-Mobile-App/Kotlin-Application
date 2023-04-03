package com.example.kotlin_application.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin_application.data.Forum
import com.example.kotlin_application.data.UserProfile
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch


class UserProfileViewModel : ViewModel() {

    //Database for user profile from firestore
    val userProfileDB = FirebaseFirestore.getInstance().collection("user_profile");
    private val _singleUserProfile = MutableLiveData<UserProfile?>(null);
    val singleUserProfile: MutableLiveData<UserProfile?> = _singleUserProfile;

    //Fetch single user profile
    fun fetchSingleUserProfile (userId : String) {

        userProfileDB.whereEqualTo("userId", userId).get().addOnSuccessListener { querySnapshot ->

            querySnapshot.documents.map { it ->
                val singleUser = UserProfile(it.id, it.getString("username").toString(), it.getString("image").toString(), it.getString("userId").toString());
                singleUserProfile.value = singleUser;
            }
        }
    }

    //Fetch single user profile based on single user profile id
    fun fetchSingleUserProfileByProfileId (userProfileId: String) {
        viewModelScope.launch {
            userProfileDB.document(userProfileId).get().addOnSuccessListener { querySnapshot ->
                if (querySnapshot.exists()) {
                    val userProfile = UserProfile(querySnapshot.id, querySnapshot.getString("username").toString(), querySnapshot.getString("image").toString(), querySnapshot.getString("userId").toString());
                    singleUserProfile.value = userProfile;
                }

            }
        }
    }

    //Update new username profile based on single user profile id
    fun updateUsername (userProfileId: String, usernameInput : String, context: Context) {
        viewModelScope.launch {
            userProfileDB.document(userProfileId).get().addOnSuccessListener { querySnapshot ->
                if (querySnapshot.exists()) {
                    val updatedUsername = UserProfile(querySnapshot.id, usernameInput, querySnapshot.getString("image").toString(), querySnapshot.getString("userId").toString());
                    userProfileDB.document(userProfileId).set(updatedUsername).addOnSuccessListener {
                        Toast.makeText(context, "Update username successfully", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }


}