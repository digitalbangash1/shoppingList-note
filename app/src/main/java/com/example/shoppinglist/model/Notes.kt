package com.example.shoppinglist.model

import com.google.firebase.Timestamp

data class Notes(
    val userId:String="",
    val title:String="",
    val description:String="",
    val timestamp: Timestamp,
    val colorIndex:Int=0,
    val docId:String=""

)
