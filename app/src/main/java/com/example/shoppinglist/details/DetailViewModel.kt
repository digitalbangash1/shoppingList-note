package com.example.shoppinglist.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.model.Notes
import com.example.shoppinglist.repository.MyStrogeRepository
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser

class DetailViewModel(
    private val repository: MyStrogeRepository
):ViewModel() {
 var detailUiState by mutableStateOf(DetailUiState())
  private set


    private val hadUser:Boolean
      get()= repository.hadUser()

    private val user:FirebaseUser?
        get()= repository.user()


    fun onColorChange(colorIndex: Int){
        detailUiState = detailUiState.copy(colorIndex= colorIndex)
    }

    fun onTitleChange(title :String){
        detailUiState =detailUiState.copy(title =title)
    }

    fun onNoteChange(note :String){
        detailUiState =detailUiState.copy(note =note)
    }



    fun addNote(){
        if(hadUser){
            repository.addNote(
               userId= user!!.uid,
               title = detailUiState.title,
               description = detailUiState.note,
               color = detailUiState.colorIndex,
               timestamp = Timestamp.now()
            ){
                detailUiState= detailUiState.copy(noteAddedStatus = it)

            }
        }
    }


    fun setEditFields(note: Notes){
        detailUiState =detailUiState.copy(
            colorIndex = note.colorIndex,
            title = note.title,
            note =note.description
        )
    }


    fun getNote(noteId:String){
        repository.getNote(noteId =noteId, onError = {}){
            detailUiState =detailUiState.copy(noteSelected = it)
            detailUiState.noteSelected?.let { itt-> setEditFields(itt) }
        }
    }

    fun updateNote(noteId:String){
        repository.updateNote(
            title = detailUiState.title,
            note = detailUiState.note,
            noteId =noteId,
            color = detailUiState.colorIndex
        ){
            detailUiState =detailUiState.copy(noteUpdatedStatus =it)
        }
    }


    fun resetNoteAdded(){
        detailUiState =detailUiState.copy(
            noteAddedStatus = false,
            noteUpdatedStatus = false)
    }

    fun resetState(){
        detailUiState =DetailUiState()
    }





}

data class DetailUiState(
    val colorIndex:Int =0,
    val title:String ="",
    val note:String ="",
    val noteAddedStatus:Boolean = false,
    val noteUpdatedStatus:Boolean = false,
    val noteSelected: Notes? = null,
)