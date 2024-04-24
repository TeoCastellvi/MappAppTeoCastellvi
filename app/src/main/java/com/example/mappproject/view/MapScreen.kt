package com.example.mappproject.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mappproject.Routes.Routes
import com.example.mappproject.models.Marcador
import com.example.mappproject.viewModel.MyViewModel
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import java.util.UUID


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(navigationController:NavController, myViewModel: MyViewModel, state: DrawerState) {
    val markers by myViewModel.markers.observeAsState(emptyList())
    val scope = rememberCoroutineScope()
    val state2 by myViewModel.showBottomSheet.observeAsState(false)
    Column {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
                .border(BorderStroke(3.dp, SolidColor(Color.Black)))
        ) {
            val itb = LatLng(41.4534265, 2.1837151)
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(itb, 10f)
            }

            Box {
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    onMapClick = {},
                    onMapLongClick = {myViewModel.ChangeState()
                    myViewModel.currentLatLng.value = it}
                ) {

                    markers?.forEach { newMarker ->
                        Marker(
                            state = MarkerState(
                                position = newMarker.posicion,

                                ),
                            title = newMarker.nombre,
                            snippet = newMarker.descripcion,
                            icon = resizeMarkerIcon(
                                context = LocalContext.current, icon = newMarker.icono
                            )
                        )
                    }
                }
                Icon(
                    Icons.Filled.Menu,
                    tint = Color.Black,
                    contentDescription ="Menu",
                    modifier= Modifier
                        .size(70.dp)
                        .padding(10.dp)
                        .clickable {
                            scope.launch {
                                state.open()
                            }
                        }
                )
            }
            if(state2) {
                BottomSheet(onDismiss = { myViewModel.ChangeState() }, myViewModel, navigationController)
            }
        }
    }
}
fun resizeMarkerIcon(context: Context, icon: Int): BitmapDescriptor {
    val bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, icon)
    val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, false)
    return BitmapDescriptorFactory.fromBitmap(resizedBitmap)

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDrawer(myViewModel: MyViewModel) {
    val state: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navigationController = rememberNavController()
    ModalNavigationDrawer(
        drawerState = state,
        gesturesEnabled = false,
        drawerContent = {
            ModalDrawerSheet {
                Row(modifier = Modifier.padding(16.dp) ) {
                    Icon(Icons.Filled.ArrowBackIosNew, contentDescription ="Back", modifier = Modifier
                        .clickable {
                            scope.launch {
                                state.close()
                            }
                        }
                        .size(25.dp))
                    Text("MENÚ", fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.padding(horizontal = 8.dp))
                }
                Divider()
                NavigationDrawerItem(
                    label = { Text(text = "Listado de marcadores") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            navigationController.navigate(Routes.Listado.route)
                            state.close()
                        }
                    }
                )

                NavigationDrawerItem(
                    label = { Text(text = "Cerrar sesion") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            navigationController.navigate(Routes.LoginScreen.route)
                            myViewModel.logOut()
                            state.close()
                        }
                    }
                )
            }
        }
    ) {
        MyScafold(state, myViewModel, navigationController )
    }
}

@Composable
fun MyScafold(state: DrawerState, myViewModel: MyViewModel, navigationController: NavController) {
    val bottomSheet by myViewModel.showBottomSheet.observeAsState(false)
    Scaffold(
        topBar = {},
        bottomBar = {},
        content = {paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (bottomSheet){
                    BottomSheet(onDismiss = {myViewModel.ChangeState()}, myViewModel, navigationController)
                }
                NavHost(
                    navController = navigationController as NavHostController,
                    startDestination = Routes.LoginScreen.route
                ) {
                    composable(Routes.MapScreen.route) { MapScreen(navigationController, myViewModel, state) }
                    composable(Routes.Listado.route) { Listado(myViewModel, navigationController) }
                    composable(Routes.TakePhotoScreen.route) { TakePhotoScreen(navigationController, myViewModel) }
                    composable(Routes.Detail.route) { Detail(myViewModel, navigationController) }
                    composable(Routes.EditScreen.route) { EditScreen(myViewModel, navigationController) }
                    composable(Routes.LoginScreen.route) { LogInScreen(navigationController, myViewModel) }
                    composable(Routes.RegisterScreen.route) { RegisterScreen(navigationController, myViewModel) }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(onDismiss: () -> Unit, myViewModel: MyViewModel, navigationController: NavController) {
    val botomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = botomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        containerColor = Color(93, 217, 193),
        modifier = Modifier.fillMaxWidth(),

        ) {
        MarkerCreator(myViewModel, navigationController)
    }

}


@Composable
fun MarkerCreator(myViewModel: MyViewModel,  navigationController: NavController) {
    var name by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    val currentLatLng by myViewModel.currentLatLng.observeAsState(LatLng(0.0, 0.0))
    val iconos = myViewModel.iconos
    var id by remember { mutableStateOf("")    }

    var selectedIconNum by remember { mutableIntStateOf(0) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        TextField(value = name, onValueChange = { name = it }, placeholder = {
            Text(
                "Nombre del marcador", color = Color(25, 9, 51)
            )
        }, colors = TextFieldDefaults.colors(
            cursorColor = Color(25, 9, 51),
            focusedIndicatorColor = Color(172, 252, 217),
            unfocusedIndicatorColor = Color(
                172, 252, 217
            ),
            unfocusedContainerColor = Color(172, 252, 217),
            focusedContainerColor = Color(172, 252, 217),
            focusedTextColor = Color(25, 9, 51),
        ), shape = RoundedCornerShape(8.dp)
        )
        Spacer(modifier = Modifier.padding(8.dp))

        TextField(value = descripcion, onValueChange = { descripcion = it }, placeholder = {
            Text(
                "Descripción del marcador", color = Color(25, 9, 51)
            )
        }, colors = TextFieldDefaults.colors(
            cursorColor = Color(25, 9, 51),
            focusedIndicatorColor = Color(172, 252, 217),
            unfocusedIndicatorColor = Color(
                172, 252, 217
            ),
            unfocusedContainerColor = Color(172, 252, 217),
            focusedContainerColor = Color(172, 252, 217),
            focusedTextColor = Color(25, 9, 51),
        )
        )


        Spacer(modifier = Modifier.padding(8.dp))

        Row {
            iconos.forEach { icon ->
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp))

                ) {
                    Image(painter = painterResource(id = icon),
                        contentDescription = "icon",
                        modifier = Modifier
                            .size(50.dp)
                            .clickable {
                                selectedIconNum = iconos.indexOf(icon)
                            })
                }
            }
        }
        Button(
            modifier = Modifier
                .padding(top = 16.dp, bottom = 0.dp)
                .fillMaxWidth(0.8f),
            onClick = {
                id = UUID.randomUUID().toString()
                val newMarker =
                    Marcador(name, descripcion, currentLatLng, iconos[selectedIconNum], id)
                myViewModel.addMarker(newMarker)
                myViewModel.showBottomSheet.value = false
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(172, 252, 217), contentColor = Color(25, 9, 51)
            ),
            shape = RoundedCornerShape(8.dp)

        ) {
            Text(text = "Agregar marcador")

        }
        CameraScreen(navigationController , myViewModel)

    }
}


@Composable
fun CameraScreen (navigationController: NavController, myViewModel: MyViewModel) {
    val context = LocalContext.current
    val isCameraPermissionGranted by myViewModel.cameraPermissionGranted.observeAsState(false)
    val shouldShowPermissionRationale by myViewModel.shouldShowPermissionRationale.observeAsState(false)
    val showpermissionDenied by myViewModel.showpermissionDenied.observeAsState(false)

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                myViewModel.setCameraPermissionGranted(true)
            } else {
                myViewModel.setShouldShowPermissionRationale(
                    shouldShowRequestPermissionRationale(
                        context as Activity,
                        android.Manifest.permission.CAMERA
                    )
                )
                if (!shouldShowPermissionRationale) {
                    Log.i("CameraScreen", "No podemos volver a pedir permisos")
                    myViewModel.setShowPermissionDenied(true)
                }
            }
        }
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(modifier = Modifier.fillMaxWidth(0.8f),
            onClick = {
            if (!isCameraPermissionGranted) {
                launcher.launch(android.Manifest.permission.CAMERA)
            } else {
                navigationController.navigate(Routes.TakePhotoScreen.route)
            }
        },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(172, 252, 217), contentColor = Color(25, 9, 51)
            ),
            shape = RoundedCornerShape(8.dp)) {
            Text(text = "Take photo")
        }
    }
    if(showpermissionDenied){
        PermissionDeclinedScreen()
    }

}

@Composable
fun PermissionDeclinedScreen() {
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()

    ) {

        Text(text = "Permission required", fontWeight = FontWeight.Bold)
        Text(text = "This app needs access to the camera to take photos")
        Button(onClick = {
            openAppSettings (context as Activity)
        }) {
            Text(text = "Accept")
        }
    }
}


fun openAppSettings(activity: Activity) {
    val intent = Intent().apply {
        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        data = Uri.fromParts("package", activity.packageName, null)
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    activity.startActivity(intent)
}

