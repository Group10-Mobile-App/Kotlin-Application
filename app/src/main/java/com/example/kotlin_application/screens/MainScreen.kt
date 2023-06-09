package com.example.kotlin_application.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.example.kotlin_application.data.BottomNavItem
import com.example.kotlin_application.data.MenuItem
import com.example.kotlin_application.navigation.BottomNavigationBar
import com.example.kotlin_application.navigation.Drawer
import com.example.kotlin_application.navigation.DrawerBody
import com.example.kotlin_application.navigation.Screens
import com.example.kotlin_application.viewmodel.ForumViewModel
import com.example.kotlin_application.viewmodel.UserProfileViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch


@ExperimentalCoilApi
@ExperimentalPermissionsApi
@ExperimentalComposeUiApi
@Composable
fun MainScreen(navController: NavController) {
    //Get user profile viewModel
    val userProfileViewModel: UserProfileViewModel = viewModel()
    //Get Forum viewModel
   val viewModel: ForumViewModel = viewModel()
    //List of forum data
    val state = viewModel.forum;

    //Scaffold
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    //Check user is logged in or not
    val checkUserIsNull = remember(FirebaseAuth.getInstance().currentUser?.email) {
        FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()
    }

    //Get uid from firebase
    val uid = FirebaseAuth.getInstance().uid.toString();


    val context = LocalContext.current;

    //Set effect to set username for title bar
    //Set effect to fetch single user id

    LaunchedEffect(uid, userProfileViewModel, checkUserIsNull) {
        userProfileViewModel.fetchSingleUserProfile(uid);
    }

    //Get state from user profile from view model
//    val single_user = userProfileViewModel.singleUserProfile;

    val single_user = userProfileViewModel.singleUserProfile.observeAsState();



    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            renderTopAppBar(
                single_user.value?.username,
                checkUserIsNull,
                navController,
                onNavigationIconClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            )
        }
        ,
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerContent = {
            Drawer()
            DrawerBody(
                items = listOf(
                    MenuItem(
                        id = "home",
                        title = "Home",
                        contentDescription = "Go to home screen",
                        icon = Icons.Default.Home
                    ),

                    if(!checkUserIsNull){ MenuItem(

                        id = "profile",
                        title = "Profile",
                        contentDescription = "Go to profile screen",
                        icon = Icons.Default.Image
                    )} else (
                            MenuItem(
                                id = "register",
                                title = "Register",
                                contentDescription = "Register",
                                icon = Icons.Default.AppRegistration
                            )),

                    if(!checkUserIsNull){
                        MenuItem(
                            id = "Logout",
                            title = "Logout",
                            contentDescription = "Logout",
                            icon = Icons.Default.ExitToApp
                        )
                    } else {
                        MenuItem(
                            id = "Login",
                            title = "Login",
                            contentDescription = "Logout",
                            icon = Icons.Default.AccountBox
                        )

                    },
                    MenuItem(
                        id = "settings",
                        title = "Settings",
                        contentDescription = "Go to settings screen",
                        icon = Icons.Default.Settings
                    ),
                    MenuItem(
                        id = "about",
                        title = "About Us",
                        contentDescription = "Get help",
                        icon = Icons.Default.Info
                    ),

                    )


            ) {

                //For going to the Login Page
                if (it.id == "home") {
                    navController.navigate(
                        Screens.MainScreen.name
                    )
                }
                if (it.id == "Login") {
                    navController.navigate(
                        Screens.LoginScreen.name + "/false"
                    )
                }
                if (it.id == "Logout"){ FirebaseAuth.getInstance().signOut().run {
                    navController.navigate(
                        Screens.MainScreen.name
                    )
                }}
                if (it.id == "profile") {
                    navController.navigate(
                        Screens.ProfileScreen.name
                    )
                }
                if (it.id == "register") {
                    navController.navigate(
                        Screens.LoginScreen.name + "/true"
                    )
                }

            }
        },

        floatingActionButton = { renderFloatingButtonAction(navController)},
        bottomBar = {
            Modifier.padding(top = 100.dp)
            BottomNavigationBar(
                items = listOf(
                    BottomNavItem(
                        name = "Home",
                        route = "home",
                        icon = Icons.Default.Home
                    ),

                    BottomNavItem(
                        name = "Direct",
                        route = "direct",
                        icon = Icons.Default.MarkChatUnread,
                        badgeCount = 0
                    ),
                    BottomNavItem(
                        name = "Chat",
                        route = "chat",
                        icon = Icons.Default.Chat,
                        badgeCount = 0
                    ),
                    BottomNavItem(
                        name = "Search",
                        route = "search",
                        icon = Icons.Default.Search,
                        badgeCount = 0
                    )
                ),
                navController = navController,
                onItemClick = {
                    if(it.name == "Search"){ navController.navigate(Screens.SearchScreen.name)}
                    if(it.name == "Direct" && !checkUserIsNull){ navController.navigate(Screens.ChatListScreen.name)}
                    if (it.name == "Direct" && checkUserIsNull){
                        Toast.makeText(context, "Not Logged in. Log in to use", Toast.LENGTH_LONG).show()}
                    if(it.name == "Chat"){ navController.navigate(Screens.ChatScreen.name)}
                    if(it.name == "Home"){ navController.navigate(Screens.MainScreen.name)}
                }
            )
        }
    ) {
        it

        //Fetch single forum data with LazyColumn
            LazyColumn(modifier = Modifier
                .padding(2.dp)
                .padding(it)

            ) {
                items(state) {
                        item ->
                    SingleForum(item = item, navController = navController, uid = uid)
                    Spacer(modifier = Modifier.height(20.dp))

                }
            }




    }

}

@ExperimentalComposeUiApi
@Composable
fun renderFloatingButtonAction(navController: NavController) {
    val checkUserIsNull = remember(FirebaseAuth.getInstance().currentUser?.email) {
        FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()
    }
    if(!checkUserIsNull){
    FloatingActionButton(
        onClick = { navController.navigate(Screens.ForumPost.name) },
        shape = RoundedCornerShape(50.dp),
        contentColor = MaterialTheme.colors.onSecondary,
        backgroundColor = MaterialTheme.colors.onBackground
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Go to ForumPost")
    }}
}
@ExperimentalComposeUiApi
@Composable
fun renderTopAppBar(
    username: String?,
    checkUserIsNull: Boolean,
    navController: NavController,
    onNavigationIconClick: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Toggle drawer",
                )
            }
        },
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                if (!checkUserIsNull) {
                    Text(text = "Welcome $username to Agora")
                } else {
                    Text(text = "Welcome to Agora")
                }
            }
        },
        backgroundColor = MaterialTheme.colors.onBackground,
        contentColor = MaterialTheme.colors.onSecondary
    )
}
