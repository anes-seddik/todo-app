package com.example.todo3.backend

import androidx.lifecycle.LiveData

class TodoRepositoryNoteRepository(private val notesDao: todoDAO) {

    // on below line we are creating a variable for our list
    // and we are getting all the notes from our DAO class.
    val allNotes: LiveData<List<todos>> = notesDao.getNotes()

    // on below line we are creating an insert method
    // for adding the note to our database.
    suspend fun insert(note: todos) {
        notesDao.addNote(note)
    }

    // on below line we are creating a delete method
    // for deleting our note from database.
    suspend fun delete(note: todos){
        notesDao.deleteNote(note)
    }
    suspend fun deleteAllTodos(){
        notesDao.deleteAllTodos()
    }

    // on below line we are creating a update method for
    // updating our note from database.
    suspend fun update(note: todos){
        notesDao.updateNote(note)
    }
}