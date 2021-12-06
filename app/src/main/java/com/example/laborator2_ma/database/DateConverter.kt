package com.example.laborator2_ma.database

import androidx.room.TypeConverter
import com.example.laborator2_ma.domain.WorkoutExerciseType
import java.util.*

class DateConverter {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let {
            Date(it)
        }
    }

    @TypeConverter
    fun toTimeStamp(date: Date?): Long? {
        return date?.time
    }
}

class ExerciseTypeConverter {

    @TypeConverter
    fun toExerciseType(value: String): WorkoutExerciseType {
        return enumValueOf(value)
    }

    @TypeConverter
    fun fromExerciseType(exerciseType: WorkoutExerciseType): String {
        return exerciseType.name
    }
}