package com.example.mappproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.mappproject.ui.theme.MappProjectTheme
import com.example.mappproject.view.MyDrawer
import com.example.mappproject.viewModel.MyViewModel



//https://coolors.co/bbdb9b-abc4a1-9db4ab-8d9d90-878e76
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val myViewModel by viewModels<MyViewModel>()
        setContent {
            MappProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(223, 235, 253, 255)
                ) {
                    MyDrawer(myViewModel)
                }
            }
        }
    }
}


