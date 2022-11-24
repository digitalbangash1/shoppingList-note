package com.example.shoppinglist.screen.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.model.Notes
import com.example.shoppinglist.repository.MyStrogeRepository
import com.example.shoppinglist.repository.Resources

class HomeViewModel(
    private val repository: MyStrogeRepository
) : ViewModel (){
    val homeUiState by mutableStateOf(HomeUiState())

    val user = repository.user()
    val hasUser : Boolean
        get() = repository.hadUser()

    private val userId : String
        get() = repository.getUserId()



}

data class HomeUiState (
    val noteLis :Resources<List<Notes>> = Resources.Loading(),
    val noteDeletedStatus : Boolean = false

        )