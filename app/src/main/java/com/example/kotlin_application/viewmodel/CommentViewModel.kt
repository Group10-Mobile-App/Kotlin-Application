package com.example.kotlin_application.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin_application.data.Comment
import com.example.kotlin_application.data.CommentInput
import com.example.kotlin_application.data.Forum
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CommentViewModel : ViewModel() {

    //Comment DB
    val commentDB = FirebaseFirestore.getInstance().collection("comment")

    //State for lists of comments
    val comments = mutableStateListOf<Comment?>();


    fun fetchComments (forumId : String) {
        viewModelScope.launch {

           commentDB.whereEqualTo("forumId", forumId).get().addOnSuccessListener { result ->
               val commentList = mutableStateListOf<Comment?>();
               result.documents.map { document ->
                   val newComment = Comment(document.id, document.getString("comment").toString(), document.getTimestamp("createdAt"), document.getString("forumId").toString(), document.getString("userId").toString(), document.getString("username").toString())
                   commentList.add(newComment);
               }

               comments.clear();
               comments.addAll(commentList)

           }
        }
    }

    fun saveComment (commentInput: CommentInput, context: Context) {
        viewModelScope.launch {
            commentDB.add(commentInput).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Add comment succesfully!", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Add comment fail!", Toast.LENGTH_LONG).show()
                }
            }.addOnFailureListener {
                Toast.makeText(context, "Add comment fail!", Toast.LENGTH_LONG).show()
            }
        }
    }
}