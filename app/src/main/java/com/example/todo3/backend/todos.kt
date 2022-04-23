package com.example.todo3.backend

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "todos")
data class todos(

    val todotitle: String,
    var checkState: Boolean = false,
    @PrimaryKey(autoGenerate = true)var id :Int =0

): Parcelable
