package com.example.shoppinglist.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.model.Notes
import com.example.shoppinglist.repository.Resources
import com.example.shoppinglist.ui.theme.Utils
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*

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
        topBar = {
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
                title = {
                    Text(text = "Home")
                }
            )
        }


    ) { padding ->
        Column(
            modifier = Modifier.padding(padding)
        ) {
            when (homeUiState.notesList) {
                is Resources.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(align = Alignment.Center)

                    )

                }
                is Resources.Success -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(16.dp),
                    ) {
                        items(
                            homeUiState.notesList.data ?: emptyList()
                        ) { note ->
                            NoteItem(
                                notes = note,
                                onLongClick = {
                                    selectedNote = note
                                    openDialog = true
                                },
                            ) {
                                onNoteClick.invoke(note.docId)
                            }

                        }

                    }
                    AnimatedVisibility(visible = openDialog) {
                        AlertDialog(
                            onDismissRequest = {
                                openDialog = false
                            },
                            title = {
                                Text(text = "Delete Note?")
                            },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        selectedNote?.docId?.let {
                                            homeViewModel?.deleteNote(it)
                                        }
                                        openDialog = false
                                    }
                                ) {
                                    Text(text = "Delete")
                                }
                            },
                            dismissButton = {
                                Button(
                                    onClick = {
                                        openDialog = false
                                    }
                                ) {
                                    Text(text = "Cancel")
                                }
                            }
                        )
                    }

                }
                else -> {
                    Text(
                        text = homeUiState.notesList.throwable?.localizedMessage
                            ?: "Something went wrong",
                        color = Color.Red
                    )

                }
            }

        }


    }
    
    LaunchedEffect(key1 = homeViewModel?.hasUser){
        if(homeViewModel?.hasUser == false){
            navToLoginPage.invoke()
        }
    }


}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteItem(
    notes: Notes,
    onLongClick: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .combinedClickable(
                onClick = { onClick.invoke() },
                onLongClick = { onLongClick.invoke() }
            )
            .padding(8.dp)
            .fillMaxWidth(),
        backgroundColor = Utils.colors[notes.colorIndex]

    ) {
        Column {
            Text(
                text = notes.title,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Clip,
                modifier = Modifier.padding(4.dp)
            )
            Spacer(modifier = Modifier.size(4.dp))
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.disabled) {

                Text(
                    text = notes.description,
                    style = MaterialTheme.typography.body1,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(4.dp)
                )
            }
            Spacer(modifier = Modifier.size(4.dp))
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.disabled) {

                Text(
                    text = formatDate(notes.timestamp),
                    style = MaterialTheme.typography.body1,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(4.dp)
                        .align(Alignment.End)
                )
            }

        }

    }
}

private fun formatDate(timestamp: Timestamp): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.getDefault())
    return sdf.format(timestamp.toDate())
}


@Preview
@Composable
fun HomePreview() {
    Home(homeViewModel = null, onNoteClick =, navToDetailPage = { /*TODO*/ }) {

    }
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