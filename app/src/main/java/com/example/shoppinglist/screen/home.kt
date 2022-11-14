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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.uiComponents.PgIcon
import com.example.shoppinglist.uiComponents.PgIconButton
import com.example.shoppinglist.uiComponents.PgTransparentFooter
import com.example.shoppinglist.R


@Composable
fun Footer(
    onAddNewListClick: () -> Unit,
    //onAddNewGroupClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    PgTransparentFooter(modifier) {
        val shape = MaterialTheme.shapes.large
        Surface(
            modifier = Modifier
                .height(48.dp)
                .weight(1f)
                .clip(shape)
                .clickable(onClick = onAddNewListClick),
            shape = shape,
            color = Color.Transparent,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 10.dp, end = 16.dp)
            ) {
                PgIcon(
                    imageVector = Icons.Rounded.Add,
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = stringResource(R.string.todo_new_list),
                    style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.SemiBold),
                )
            }
        }

        Spacer(Modifier.width(8.dp))

        /*PgIconButton(onClick = onAddNewGroupClick, color = Color.Transparent) {
            PgIcon(imageVector = Icons.Rounded.create_new_folder)
        }*/
    }
}

// Language: kotlin