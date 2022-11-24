package com.example.shoppinglist.repository

import com.example.shoppinglist.model.Notes
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ListenerRegistration

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import com.google.firebase.Timestamp


const val NOTES_COLLECTION = "notes"

class MyStrogeRepository(){

    fun user() = Firebase.auth.currentUser
    fun hadUser():Boolean =Firebase.auth.currentUser != null

    fun getUserId():String =Firebase.auth.currentUser?.uid.orEmpty()

      private val notesRef:CollectionReference =Firebase.firestore.collection(NOTES_COLLECTION)







    @OptIn(ExperimentalCoroutinesApi::class)
    fun getUserNotes(
        userId:String
    ): Flow<Resources<List<Notes>>> = callbackFlow{

        var snapshotStateListener: ListenerRegistration? =null
        try{
            snapshotStateListener = notesRef
                .orderBy("timestamp")
                .whereEqualTo("userId",userId)
                .addSnapshotListener{
                    snapshot,e ->
                    val response =
                        if (snapshot != null){
                        val notes = snapshot.toObjects(Notes::class.java)
                        Resources.Success(data = notes)
                        } else{
                        Resources.Error(throwable = e?.cause)
                    }
                    trySend( response)
                }


        }catch (e:Exception){
            trySend(Resources.Error(e?.cause))
            e.printStackTrace()

        }

        awaitClose{
            snapshotStateListener?.remove()
        }



    }




    fun getNote(noteId:String, onError:(Throwable?)->Unit, onSuccess: (Notes?)-> Unit)
    {
        notesRef
            .document(noteId)
            .get()
            .addOnSuccessListener {
            onSuccess.invoke(it.toObject(Notes::class.java))
        }
            .addOnFailureListener {
                result -> onError.invoke(result.cause)
            }
    }






    fun addNote(
        userId: String,
        title:String,
        description:String,
        timestamp: Timestamp,
        color: Int = 0,
        onComplete:(Boolean)-> Unit,

    ){
        val docId = notesRef.document().id
        val note = Notes(
            userId,
            title,
            description,
            timestamp,
            colorIndex = color,
            docId = docId

        )
        notesRef
            .document(docId)
            .set(note)
            .addOnCompleteListener{ result ->
                onComplete.invoke( result.isSuccessful)

            }

    }





    fun DeleteNote(noteId: String, onComplete: (Boolean) -> Unit){
        notesRef.document(noteId)
            .delete()
            .addOnCompleteListener{
                onComplete.invoke(it.isSuccessful)
            }
    }




    fun UpdateNote(
        title: String,
        note:String,
        color: Int,
        noteId:String,
        onResult:(Boolean)-> Unit
    ){

        val updateData = hashMapOf<String,Any>(
            "colorIndex" to color,
            "description" to note,
            "title" to title
        )
        notesRef.document(noteId)
            .update(updateData)
            .addOnCompleteListener{
                onResult(it.isSuccessful)
            }
    }


}


sealed class Resources<T>(
    val data: T? = null,
    val throwable: Throwable? = null,
){
    class Loading<T>:Resources<T>()
    class Success<T>(data: T?):Resources<T>(data = data)
    class Error<T>(throwable: Throwable?):Resources<T>(throwable = throwable)

}