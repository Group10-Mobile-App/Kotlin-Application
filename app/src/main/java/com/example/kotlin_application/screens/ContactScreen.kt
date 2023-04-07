package com.example.kotlin_application.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.kotlin_application.navigation.Screens
import com.example.kotlin_application.ui.theme.goldYellowHex
import com.example.kotlin_application.viewmodel.ChatVIewModel

@ExperimentalComposeUiApi
@Composable
fun ContactScreen (navController: NavController, userIds : List<String>?, chatId : String?) {
    val chatViewModel : ChatVIewModel = viewModel();
    val message = remember {
        mutableStateOf("");
    }
    Log.d("userIds", "${userIds}")

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack()}) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Go Back"
                        )
                    }
                },
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(text = "Chat Room")
                    }
                },
                backgroundColor = MaterialTheme.colors.onBackground,
                contentColor = MaterialTheme.colors.onSecondary
            )
        },
        bottomBar = {
            OutlinedTextField(
                value = message.value,
                onValueChange = {
                                message.value = it
                },
                label = {
                    Text(
                        "Type Your Message", style = TextStyle(color = MaterialTheme.colors.onBackground)
                    )
                },
                maxLines = 1,
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 1.dp)
                    .fillMaxWidth(),
                //.weight(weight = 0.09f,fill = true ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                placeholder = {
                       Text(text = "Enter your message...!", style = TextStyle(color = goldYellowHex))
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.onBackground,
                    unfocusedBorderColor = MaterialTheme.colors.onBackground,
                    disabledBorderColor = MaterialTheme.colors.onBackground
                ),
                singleLine = true,
                trailingIcon = {
                    IconButton(
                        onClick = {
                            Log.d("Send", "Send successfully!")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Send Button",
                            tint = MaterialTheme.colors.onBackground
                        )
                    }

                }
            )

        }
    ) {
        it
    }

}


