package com.example.mappproject.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
fun Detail(myViewModel: MyViewModel, navController: NavController) {
    val marker by myViewModel.selectedMarker.observeAsState()
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(172, 252, 217))) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
                .background(Color(93, 217, 193))
        ){
            Icon(
                Icons.Filled.ArrowBackIosNew,
                tint = Color(25, 9, 51),
                contentDescription ="Back",
                modifier= Modifier
                    .size(60.dp)
                    .padding(10.dp)
                    .clickable {
                        navController.navigate(Routes.Listado.route)
                    }
            )
            Text(
                text = "Detalles",
                color = Color(25, 9, 51),
                textAlign = TextAlign.Center,
                fontSize = 27.sp,
                modifier = Modifier
                    .padding(10.dp)
                    .padding(start = 20.dp))
        }
        Row(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
        ) {

        }
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize(0.9f)
                .clip(RoundedCornerShape(30.dp))
                .background(color = Color(93, 229, 193))
                .border(3.5.dp, Color(93, 217, 193), shape = RoundedCornerShape(30.dp))
        ) {
            marker?.let { painterResource(id = it.icono) }?.let {
                Image(painter = it,
                    contentDescription = "icon",
                    modifier = Modifier
                        .size(250.dp)
                        .padding(top = 20.dp)
                )
            }
            marker?.let { Text(text = it.nombre, textAlign = TextAlign.Center, fontSize = 40.sp) }
            marker?.let { Text(text = it.nombre, textAlign = TextAlign.Center, fontSize = 25.sp) }
        }
    }
}