package com.example.laborator2_ma.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.laborator2_ma.databinding.AddActivityBinding
import com.example.laborator2_ma.domain.WorkoutExercise
import com.example.laborator2_ma.domain.WorkoutExerciseType
import com.example.laborator2_ma.utils.toast
import com.example.laborator2_ma.viewmodels.WorkoutViewModel

class AddExerciseWorkoutActivity : AppCompatActivity() {

    private lateinit var binding: AddActivityBinding
    private lateinit var workoutViewModel: WorkoutViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        workoutViewModel = ViewModelProvider(this).get(WorkoutViewModel::class.java)

        val bundle: Bundle? = intent.extras
        var workoutSetId = 0
        if (bundle != null) {
            workoutSetId = bundle.getInt(MainActivity.MAIN_ACTIVITY_WORKOUT_SET_ID)
        }

        binding = AddActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonAddExerciseWorkout.setOnClickListener {
            toast("Exercise has been added successfully!")
            val name = binding.addWorkoutExerciseNameEditText.text.toString()
            val numberOfSets =
                binding.addWorkoutExerciseNumberOfSetsEditText.text.toString().toInt()
            val numberOfReps =
                binding.addWorkoutExerciseNumberOfRepsEditText.text.toString().toInt()
            val weight = binding.addWorkoutExerciseWeightEditText.text.toString().toFloat()

            val exerciseType = enumValueOf<WorkoutExerciseType>(
                binding.addWorkoutExerciseTypeEditText.text.toString().uppercase()
            )
            workoutViewModel.addWorkoutExercise(
                WorkoutExercise(
                    0,
                    name,
                    numberOfReps,
                    numberOfSets,
                    weight,
                    exerciseType,
                    workoutSetId
                )
            )
            finish()
        }
    }
}