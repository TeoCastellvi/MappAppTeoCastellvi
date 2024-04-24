package com.example.mappproject.viewModel

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mappproject.R
import com.example.mappproject.firebase.Repository
import com.example.mappproject.models.Marcador
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.auth.User

class MyViewModel: ViewModel() {
    private val _goToNext = MutableLiveData(false)
    val goToNext =_goToNext
    private val _userId = MutableLiveData<String>()
    val userId = _userId
    private val _logeado = MutableLiveData(false)
    val logeado = _logeado
    private val _loggedUser = MutableLiveData<String>()
    val loggedUser = _loggedUser
    private val _password = MutableLiveData<String>()
    val password = _password
    private val _email = MutableLiveData<String>()
    val email = _email
    private val auth = FirebaseAuth.getInstance()
    val repository = Repository()
    private val _clicado = MutableLiveData<Boolean>(false)
    val clicado = _clicado
    private val _markers = MutableLiveData<MutableList<Marcador>>(mutableListOf())
    val markers = _markers
    private val _showBottomSheet = MutableLiveData(false)
    val showBottomSheet = _showBottomSheet
    private val _currentLatLng = MutableLiveData<LatLng>()
    val currentLatLng = _currentLatLng
    private val _photoTaken = MutableLiveData<Bitmap?>()
    val photoTaken = _photoTaken
    private val _selectedMarker = MutableLiveData<Marcador>()
    val selectedMarker = _selectedMarker
    private val _imageList = MutableLiveData<MutableList<String>>(mutableListOf())
    val imageList = _imageList
    val iconos = listOf(
        R.drawable.autobus,
        R.drawable.gimnasio,
        R.drawable.restaurante,
        R.drawable.supermercado,
        R.drawable.policia
    )

    fun addMarker(marker: Marcador) {
        _markers.value?.apply { add(marker) }
        repository.addMarker(marker)
    }

    fun editMarker(marker: Marcador) {
       repository.editMarker(marker)
    }

    fun ChangeState() {
        _showBottomSheet.value = !_showBottomSheet.value!!
    }

    private val _cameraPermissionGranted = MutableLiveData(false)
    val cameraPermissionGranted = _cameraPermissionGranted

    private val _shouldShowPermissionRationale = MutableLiveData(false)
    val shouldShowPermissionRationale = _shouldShowPermissionRationale

    private val _showPermissionDenied = MutableLiveData(false)
    val showpermissionDenied = _showPermissionDenied
    fun setCameraPermissionGranted(granted: Boolean) {
        _cameraPermissionGranted.value = granted
    }

    fun setShouldShowPermissionRationale(should: Boolean) {
        _shouldShowPermissionRationale.value = should
    }

    fun setShowPermissionDenied(denied: Boolean) {
        _showPermissionDenied.value = denied

    }

    fun addPhotoTaken(bitmap: Bitmap) {
        _photoTaken.value = bitmap
    }

    fun markerSelected(marker: Marcador) {
        _selectedMarker.value = marker
    }

    fun deleteMarker(marker: Marcador) {
        val uptadeMarker = markers.value?.filter { it != marker }
        repository.deleteMarker(marker)
        markers.value = uptadeMarker as MutableList<Marcador>?
    }
    fun getMarkers() {
        repository.getMarkers().addSnapshotListener { value, error ->
            if (error != null) {
                Log.e("Firestore error", error.message.toString())
                return@addSnapshotListener
            }
            val tempList = mutableListOf<Marcador>()
            for (dc: DocumentChange in value?.documentChanges!!) {
                if (dc.type == DocumentChange.Type.ADDED) {
                    val newMarker = dc.document.toObject(Marcador::class.java)
                    newMarker.nombre = dc.document.id
                    tempList.add(newMarker)
                }
            }
            _markers.value = tempList
        }
    }

    fun register(username: String, password:String) {
        auth.createUserWithEmailAndPassword(username, password)
            .addOnCompleteListener {task ->
                if (task.isSuccessful) {
                    _goToNext.value = true
                } else {
                    _goToNext.value = false
                    Log.d("Error", "Error creating user: ${task.result}")
                }
            }
            .addOnFailureListener {
                Log.d("Error", "Error al registrar el usuario: ${it.message}")
            }
    }

    fun setLogin(gonext:Boolean) {
        _goToNext.value = gonext
    }


    fun login(username:String?, password: String?) {
        auth.signInWithEmailAndPassword(username!!,password!!)
            .addOnCompleteListener {task ->
                if (task.isSuccessful) {
                    _userId.value = task.result.user?.uid
                    _loggedUser.value = task.result.user?.email?.split("@")?.get(0)
                    _goToNext.value = true
                    _logeado.value = true
                } else {
                    _logeado.value = false
                    _goToNext.value = false
                    Log.d("Error", "Error creating user: ${task.result}")
                }
            }
            .addOnFailureListener {
                Log.d("Error", "Error al registrar el usuario: ${it.message}")
            }
    }

    fun setemail(email: String) {
        _email.value = email
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun logOut() {
        _logeado.value = false
        auth.signOut()
    }
}