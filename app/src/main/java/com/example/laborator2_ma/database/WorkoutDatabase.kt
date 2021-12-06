package com.example.laborator2_ma.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.laborator2_ma.domain.WorkoutExercise
import com.example.laborator2_ma.domain.WorkoutSet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

@Database(
    entities = [WorkoutExercise::class, WorkoutSet::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class, ExerciseTypeConverter::class)
abstract class WorkoutDatabase : RoomDatabase() {

    abstract fun workoutDao(): WorkoutDao

    companion object {
        @Volatile
        private var INSTANCE: WorkoutDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): WorkoutDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WorkoutDatabase::class.java,
                    "workout_database"
                )
                    .fallbackToDestructiveMigration()
//                    .addCallback(WorkoutDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class WorkoutDatabaseCallback(private val scope: CoroutineScope) :
            RoomDatabase.Callback() {

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)

                INSTANCE?.let {
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(it.workoutDao())
                    }
                }
            }
        }

        fun populateDatabase(workoutDao: WorkoutDao) {
            val workoutSet = WorkoutSet(0, "Workout Test 1", Date())
            workoutDao.addWorkoutSet(workoutSet)
        }
    }
}