package com.kyi.knowyouringredients.core.navigation

enum class AuthDestination(val route: String) {
    Login("login"),
    SignUp("signup"),
    Main("main");

    companion object {
        const val AUTH_NAVIGATION_GRAPH = "auth_graph"
    }
}