package com.example.mappproject.firebase

import android.util.Log
import com.example.mappproject.models.Marcador
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User

class Repository {
    private val database = FirebaseFirestore.getInstance()

    fun addMarker(marcador: Marcador) {
        database.collection("marcadores")
            .add(
                hashMapOf(
                    "id" to marcador.id,
                    "nombre" to marcador.nombre,
                    "descripcion" to marcador.descripcion,
                    "posicion" to marcador.posicion,
                    "icono" to marcador.icono
                )
            )
    }

    fun editMarker(marcador: Marcador) {
        database.collection("marcadores")
            .whereEqualTo("id", marcador.id)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    database.collection("marcadores").document(document.id)
                        .update(hashMapOf(
                            "id" to marcador.id,
                            "nombre" to marcador.nombre,
                            "posicion" to marcador.posicion,
                            "descripcion" to marcador.descripcion,
                            "icono" to marcador.icono
                        ))
                }



            }
            .addOnFailureListener { exception ->
                Log.w("Error", "Error getting documents: ", exception)
            }
    }

    fun deleteMarker(marcador: Marcador) {
        database.collection("marcadores")
            .whereEqualTo("id", marcador.id)
            .get()
            .addOnSuccessListener { documents->
                for (document in documents) {
                    database.collection("marcadores").document(document.id)
                        .delete()
                }
            }
            .addOnFailureListener{ exception ->
                Log.w("Error", "Error getting documents: ", exception)
            }
    }

    fun getMarkers(): CollectionReference {
        return database.collection("marcadores")
    }  


}