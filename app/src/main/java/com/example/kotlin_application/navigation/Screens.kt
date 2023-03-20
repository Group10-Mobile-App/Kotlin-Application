package com.example.kotlin_application.navigation

enum class Screens {
    GetStartedScreen,
    LoginScreen,
    MainScreen;

    companion object {
        fun fromRoute(route: String): Screens =
            when (route?.substringBefore("/")) {
                GetStartedScreen.name -> GetStartedScreen
                LoginScreen.name -> LoginScreen
                MainScreen.name -> MainScreen
                else -> throw java.lang.IllegalArgumentException("Route $route is not recognized")
            }
    }
}