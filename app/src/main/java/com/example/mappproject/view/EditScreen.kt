package com.example.mappproject.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mappproject.Routes.Routes
import com.example.mappproject.models.Marcador
import com.example.mappproject.viewModel.MyViewModel
import com.google.android.gms.maps.model.LatLng
import java.util.UUID

@Composable
fun EditScreen(myViewModel: MyViewModel, navController: NavController) {
    var name by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    val currentLatLng by myViewModel.currentLatLng.observeAsState(LatLng(0.0, 0.0))
    val iconos = myViewModel.iconos
    var id by remember { mutableStateOf("") }

    val marcadores: List<Marcador> by myViewModel.markers.observeAsState(listOf())
    var myText by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(172, 252, 217)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
                .background(Color(93, 217, 193))
        ) {
            Icon(
                Icons.Filled.ArrowBackIosNew,
                tint = Color(25, 9, 51),
                contentDescription = "Back",
                modifier = Modifier
                    .size(60.dp)
                    .padding(10.dp)
                    .clickable {
                        navController.navigate(Routes.MapScreen.route)
                    }
            )
            Text(
                text = "Editar marcador",
                color = Color(25, 9, 51),
                textAlign = TextAlign.Center,
                fontSize = 27.sp,
                modifier = Modifier
                    .padding(10.dp)
                    .padding(start = 20.dp)
            )
        }

        var selectedIconNum by remember { mutableIntStateOf(0) }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .background(Color(93, 217, 193)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround

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
            Spacer(modifier = Modifier.padding(20.dp))

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


            Spacer(modifier = Modifier.padding(20.dp))

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
                    myViewModel.editMarker(newMarker)
                    myViewModel.showBottomSheet.value = false
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(172, 252, 217), contentColor = Color(25, 9, 51)
                ),
                shape = RoundedCornerShape(8.dp)

            ) {
                Text(text = "Actualizar marcador")

            }
        }
    }
}