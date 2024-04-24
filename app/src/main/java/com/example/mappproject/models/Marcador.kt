package com.example.mappproject.models

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng

data class Marcador(
    var nombre: String,
    val descripcion: String,
    val posicion: LatLng,
    val icono: Int,
    val id:String
)
