package com.example.kotlin_application.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin_application.data.Comment
import com.example.kotlin_application.data.CommentInput
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class CommentViewModel : ViewModel() {

    //Comment DB
    val commentDB = FirebaseFirestore.getInstance().collection("comment")

    //Like for comment DB
    val likeForCommentDB = FirebaseFirestore.getInstance().collection("like_for_comment")

    //State for lists of comments
    val comments = mutableStateListOf<Comment?>();

    private val _singleComment = MutableLiveData<Comment?>(null)
    val singleComment: MutableLiveData<Comment?> = _singleComment;

    //Fetch single comment
    fun getSingleComment (commentId : String) {
        viewModelScope.launch {
            commentDB.document(commentId).get()
                .addOnSuccessListener { documentSnapshot ->

                    if (documentSnapshot.exists()) {
                        var single_comment = Comment(documentSnapshot.id, documentSnapshot.getString("comment").toString(), documentSnapshot.getTimestamp("createdAt"), documentSnapshot.getString("forumId").toString(), documentSnapshot.getString("userId").toString(), documentSnapshot.getString("username").toString());
                        singleComment.value = single_comment;
                    }


                }
                .addOnFailureListener {

                }
        }
    }

    //Update comment
    fun updateComment (commentId: String, commentInput : String, context: Context) {
        viewModelScope.launch {
            commentDB.document(commentId).get().addOnSuccessListener { querySnapShot ->
                var updateComment = Comment(querySnapShot.id, commentInput, querySnapShot.getTimestamp("createdAt"), querySnapShot.getString("forumId").toString(), querySnapShot.getString("userId").toString(), querySnapShot.getString("username").toString());

                comments.map { if (it?.id == updateComment.id) updateComment else it};

                commentDB.document(commentId).set(updateComment).addOnCompleteListener {
                    Toast.makeText(context, "Update comment successfully", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

//   Delete comment
    fun deleteComment (commentId : String, context: Context) {
        viewModelScope.launch {
            commentDB.document(commentId).delete();
            comments.removeIf { it?.id == commentId };
            likeForCommentDB.whereEqualTo("commentId", commentId).get().addOnSuccessListener { snapShot ->
                for (document in snapShot.documents) {
                    document.reference.delete().addOnSuccessListener {
                        Log.d("Delete like for comment", "Delete successfully!")
                    }.addOnFailureListener {
                        Log.d("Delete like for comment", "Delete fail!")
                    }
                }
            }
            Toast.makeText(context, "Delete comment successfully!", Toast.LENGTH_LONG).show();
        }
    }

    //Fetch comments
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

    //Save comment
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