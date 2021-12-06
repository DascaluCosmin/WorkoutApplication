package com.example.laborator2_ma.domain

import android.annotation.SuppressLint
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "workout_set")
data class WorkoutSet(
    @PrimaryKey(autoGenerate = true) var workoutSetId: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "created_at") var createdAt: Date,
) {

    @SuppressLint("SimpleDateFormat")
    fun getFormattedDate(): String {
        return SimpleDateFormat("dd/M/yyyy").format(createdAt)
    }
}