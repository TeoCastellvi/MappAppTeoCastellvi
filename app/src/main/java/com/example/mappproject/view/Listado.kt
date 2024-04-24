package com.example.mappproject.view

import android.graphics.Paint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
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

@Composable
fun Listado(myViewModel: MyViewModel, navController: NavController) {
    val marcadores: List<Marcador> by myViewModel.markers.observeAsState(listOf())
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
                text = "Marcadores",
                color = Color(25, 9, 51),
                textAlign = TextAlign.Center,
                fontSize = 27.sp,
                modifier = Modifier
                    .padding(10.dp)
                    .padding(start = 20.dp)
            )
        }


        marcadores?.forEach { marcador ->
            Card(
                border = BorderStroke(3.5.dp, Color(93, 217, 193)),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .height(120.dp)
                    .clickable {
                        myViewModel.markerSelected(marcador)
                        navController.navigate(Routes.Detail.route)
                    }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(93, 229, 193))
                        .padding(10.dp)
                ) {
                    Image(
                        painter = painterResource(id = marcador.icono),
                        contentDescription = "icon",
                        modifier = Modifier
                            .size(100.dp)
                    )
                    Column {

                        Text(
                            text = marcador.nombre,
                            color = Color(25, 9, 51),
                            textAlign = TextAlign.Center,
                            fontSize = 25.sp,
                            modifier = Modifier.padding(10.dp)
                        )
                        Row {
                            Button(
                                onClick = { navController.navigate(Routes.EditScreen.route) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(172, 252, 217),
                                    contentColor = Color(25, 9, 51)
                                ),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = "Editar", color = Color(25, 9, 51),
                                    textAlign = TextAlign.Center
                                )
                            }

                            Button(
                                onClick = { myViewModel.deleteMarker(marcador) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(172, 252, 217),
                                    contentColor = Color(25, 9, 51)
                                ),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.padding(start = 10.dp)
                            ) {
                                Text(
                                    text = "Eliminar", color = Color(25, 9, 51),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

