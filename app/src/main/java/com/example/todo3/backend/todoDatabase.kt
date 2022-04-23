package com.example.todo3.backend

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [todos::class], version = 1, exportSchema = true)
abstract class todoDatabase : RoomDatabase() {
    abstract fun noteDao(): todoDAO

    companion object {

        @Volatile
        private var INSTANCE: todoDatabase? = null

        fun getDatabase(context: Context): todoDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            if (INSTANCE == null) {
                synchronized(this) {
                    // Pass the database to the INSTANCE
                    INSTANCE = buildDatabase(context)
                }
            }
            // Return database.
            return INSTANCE!!
        }

        private fun buildDatabase(context: Context): todoDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                todoDatabase::class.java,
                "todoDatabase"
            ).build()
        }
    }
}