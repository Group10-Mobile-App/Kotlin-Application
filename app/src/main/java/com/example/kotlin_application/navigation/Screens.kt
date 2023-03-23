package com.example.kotlin_application.navigation



enum class Screens {
    LoginScreen,
    MainScreen,
    ChatScreen,
    SettingScreen,
    ForumScreen,
    ForumPost;
    companion object {
        fun fromRoute(route: String): Screens =
            when (route?.substringBefore("/")) {
                LoginScreen.name -> LoginScreen
                MainScreen.name -> MainScreen
                ChatScreen.name -> ChatScreen
                SettingScreen.name -> SettingScreen
                ForumScreen.name -> ForumScreen
                ForumPost.name -> ForumPost

                else -> throw java.lang.IllegalArgumentException("Route $route is not recognized")
            }
    }
}