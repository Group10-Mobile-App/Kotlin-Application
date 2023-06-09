package com.example.kotlin_application.navigation



enum class Screens {
    LoginScreen,
    MainScreen,
    ForumPost,
    SingleForumScreen,
    AddCommentScreen,
    CommentScreen,
    SearchScreen,
    UpdateCommentScreen,
    ProfileScreen,
    UpdateUsernameScreen,
    ContactScreen,
    ChatListScreen,
    CameraScreen,
    ChatScreen,
    SignInScreen,
    UpdateForum;




    companion object {
        fun fromRoute(route: String): Screens =
            when (route?.substringBefore("/")) {
                LoginScreen.name -> LoginScreen
                MainScreen.name -> MainScreen
                ForumPost.name -> ForumPost
                SingleForumScreen.name -> SingleForumScreen
                AddCommentScreen.name -> AddCommentScreen
                SearchScreen.name -> SearchScreen
                CommentScreen.name ->CommentScreen
                ChatScreen.name -> ChatScreen
                UpdateCommentScreen.name -> UpdateCommentScreen
                ProfileScreen.name -> ProfileScreen
                UpdateUsernameScreen.name -> UpdateUsernameScreen
                ContactScreen.name -> ContactScreen
                ChatListScreen.name -> ChatListScreen
                CameraScreen.name -> CameraScreen
                SignInScreen.name -> SignInScreen
                UpdateForum.name -> UpdateForum
                else -> throw java.lang.IllegalArgumentException("Route $route is not recognized")
            }
    }
}