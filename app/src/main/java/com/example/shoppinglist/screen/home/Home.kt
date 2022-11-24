package com.example.shoppinglist.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.shoppinglist.model.Notes

@Composable
fun Home(
    homeViewModel: HomeViewModel?,
    onNoteClick: (id: String) -> Unit,
    navToDetailPage: () -> Unit,
    navToLoginPage: () -> Unit

) {
    val homeUiState = homeViewModel?.homeUiState ?: HomeUiState()

    var openDialog by remember {
        mutableStateOf(false)
    }
    var selectedNote: Notes? by remember {
        mutableStateOf(null)
    }
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = { navToDetailPage.invoke() }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )

            }
        },
        topBar= {
            TopAppBar(
                navigationIcon = {},
                actions = {
                          IconButton(onClick = {
                              homeViewModel?.signOut()
                              navToLoginPage.invoke()
                          }) {
                              Icon(
                                  imageVector = Icons.Default.ExitToApp,
                                  contentDescription = null,
                              )

                          }

                },
                title={}
            )
        }


    ) {

    }


}


@Preview
@Composable
fun HomePreview() {
    Home(onNavToLoginPage = {})
}


/*onNavToLoginPage:() -> Unit,
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
}*/

// Language: kotlin