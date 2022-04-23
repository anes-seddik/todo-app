package com.example.todo3.backend

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface todoDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(note: todos)


    @Update
    suspend fun updateNote(note: todos)

    @Delete
    suspend fun deleteNote(note: todos)

    @Query("DELETE FROM todos")
    suspend fun deleteAllTodos()

    @Query("SELECT * FROM todos ORDER BY id DESC")
    fun getNotes(): LiveData<List<todos>>
}