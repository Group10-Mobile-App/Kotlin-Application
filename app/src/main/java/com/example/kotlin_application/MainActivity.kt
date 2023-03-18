package com.example.kotlin_application

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kotlin_application.navigation.Navigation

import com.example.kotlin_application.ui.theme.KotlinApplicationTheme
import com.google.firebase.firestore.FirebaseFirestore

@ExperimentalComposeUiApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinApplicationTheme {

//                val db = FirebaseFirestore.getInstance();
//
//                val user: MutableMap<String, Any> = HashMap();
//
//                user["firstName"] = "Thong"
//                user["lastName"] = "Dang"
//
//                db.collection("users")
//                    .add(user)
//                    .addOnSuccessListener {
//                        Log.d("FB", "onCreate: ${it.id}")
//                    }
//                    .addOnFailureListener {
//                        Log.d("FB", "onCreate: $it")
//                    }
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Group10App()
                }
            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun Group10App () {

    androidx.compose.material.Surface (
        modifier = Modifier
            .padding(2.dp)
            .fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column (
            modifier = Modifier
                .padding(2.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Navigation()
        }
    }
}


@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KotlinApplicationTheme {
        Group10App()
    }
}
