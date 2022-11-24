package com.example.shoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shoppinglist.details.DetailViewModel
import com.example.shoppinglist.login.LoginViewModel
import com.example.shoppinglist.screen.home.HomeViewModel
import com.example.shoppinglist.ui.theme.ShoppingListTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            installSplashScreen().apply {

                setOnExitAnimationListener { splashScreenViewProvider ->
                    splashScreenViewProvider.remove()
                }
            }

            val loginViewModel = viewModel(modelClass = LoginViewModel::class.java)
            val homeViewModel = viewModel(modelClass = HomeViewModel::class.java)
            val detailViewModel = viewModel(modelClass = DetailViewModel::class.java)
            ShoppingListTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Navigation(
                        loginViewModel = loginViewModel,
                        detailViewModel = detailViewModel,
                        homeViewModel = homeViewModel

                    )
                }
                // MainSreen()
            }
        }
    }
}

