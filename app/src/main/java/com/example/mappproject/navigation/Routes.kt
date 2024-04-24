package com.example.mappproject.Routes

sealed class Routes(val route: String) {
    object MapScreen:Routes("MapScreen")
    object Listado:Routes("Listado")
    object TakePhotoScreen:Routes("TakePhotoScreen")

    object Detail:Routes("Detail")

    object EditScreen:Routes("EditScreen")

    object LoginScreen:Routes("LoginScreen")

    object RegisterScreen:Routes("RegisterScreen")

}
