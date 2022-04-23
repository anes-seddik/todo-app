package com.example.todo3.backend

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class todoViewmodel(application: Application) : AndroidViewModel(application) {

    // on below line we are creating a variable
    // for our all notes list and repository
    val allNotes : LiveData<List<todos>>
    val repository : TodoRepositoryNoteRepository

    // on below line we are initializing
    // our dao, repository and all notes
    init {
        val dao = todoDatabase.getDatabase(application).noteDao()
        repository = TodoRepositoryNoteRepository(dao)
        allNotes = repository.allNotes
    }
    // on below line we are creating a new method for adding a new note to our database
    // we are calling a method from our repository to add a new note.
    fun addNote(note: todos) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }
    // on below line we are creating a new method for deleting a note. In this we are
    // calling a delete method from our repository to delete our note.
    fun deleteNote (note: todos) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }

    fun deleteAllTodos () = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAllTodos()
    }

    // on below line we are creating a new method for updating a note. In this we are
    // calling a update method from our repository to update our note.
    fun updateNote(note: todos) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
    }



}