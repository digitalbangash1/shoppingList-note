package com.example.shoppinglist.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.uiComponents.PgIcon
import com.example.shoppinglist.uiComponents.PgIconButton
import com.example.shoppinglist.uiComponents.PgTransparentFooter
import com.example.shoppinglist.R
import com.example.shoppinglist.login.LoginViewModel

@Composable
fun Home(
    onNavToLoginPage:() -> Unit,
    loginViewModel: LoginViewModel? = null,
) {

    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Home Screen",
            style = MaterialTheme.typography.h3,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colors.primary
        )
        Button(onClick = { loginViewModel?.logout() ;onNavToLoginPage.invoke()}) {
            Text(text = "Logout")

        }
    }
}

@Preview
@Composable
fun HomePreview() {
    Home(onNavToLoginPage = {})
}




// Language: kotlin