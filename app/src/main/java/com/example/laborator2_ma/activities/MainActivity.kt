package com.example.laborator2_ma.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.laborator2_ma.databinding.ActivityMainBinding
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.recyclerview.widget.RecyclerView
import com.example.laborator2_ma.databinding.WorkoutExerciseBinding
import com.example.laborator2_ma.dependencyinjection.ApplicationContainer
import com.example.laborator2_ma.domain.WorkoutExercise
import com.example.laborator2_ma.domain.WorkoutExerciseType
import com.example.laborator2_ma.repository.WorkoutExerciseRepository
import com.example.laborator2_ma.utils.logd


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val applicationContainer = ApplicationContainer()
    private val adapter = ExerciseWorkoutAdapter()
    private val workoutExerciseRepository: WorkoutExerciseRepository = applicationContainer.getSingletonWorkoutExerciseRepository()!!

    private val addWorkoutExerciseLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        logd("Add WorkoutExercise response: ${result.resultCode}")
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            if (intent != null) {
                val intentBundle = intent.extras
                if (intentBundle != null) {
                    val id = intentBundle.getInt(AddExerciseWorkoutActivity.EXERCISE_ACTIVITY_ID)
                    var name = intentBundle.getString(AddExerciseWorkoutActivity.EXERCISE_ACTIVITY_NAME)
                    if (name == null) {
                        name = "Unknown name"
                    }

                    val numberOfSets = intentBundle.getInt(AddExerciseWorkoutActivity.EXERCISE_ACTIVITY_NUMBER_OF_SETS)
                    val numberOfReps = intentBundle.getInt(AddExerciseWorkoutActivity.EXERCISE_ACTIVITY_NUMBER_OF_REPS)
                    val weight = intentBundle.getFloat(AddExerciseWorkoutActivity.EXERCISE_ACTIVITY_WEIGHT)

                    val exerciseTypeString = intentBundle.getString(AddExerciseWorkoutActivity.EXERCISE_ACTIVITY_TYPE)
                    val exerciseType: WorkoutExerciseType = if (exerciseTypeString == null) WorkoutExerciseType.OTHER
                        else enumValueOf(exerciseTypeString)

                    adapter.addWorkoutExerciseToList(WorkoutExercise(id, name, numberOfSets, numberOfReps, weight, exerciseType))
                }
            }
        }
    }

    private val modifyWorkoutExerciseLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        logd("Modify WorkoutExercise response: ${result.resultCode}")
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            if (intent != null) {
                val intentBundle = intent.extras
                if (intentBundle != null) {
                    val position = intentBundle.getInt(ModifyExerciseWorkoutActivity.EXERCISE_ACTIVITY_POSITION)
                    val id = intentBundle.getInt(ModifyExerciseWorkoutActivity.EXERCISE_ACTIVITY_ID)
                    var name = intentBundle.getString(ModifyExerciseWorkoutActivity.EXERCISE_ACTIVITY_NAME)
                    if (name == null) {
                        name = "Unknown name"
                    }

                    val numberOfSets = intentBundle.getInt(ModifyExerciseWorkoutActivity.EXERCISE_ACTIVITY_NUMBER_OF_SETS)
                    val numberOfReps = intentBundle.getInt(ModifyExerciseWorkoutActivity.EXERCISE_ACTIVITY_NUMBER_OF_REPS)
                    val weight = intentBundle.getFloat(ModifyExerciseWorkoutActivity.EXERCISE_ACTIVITY_WEIGHT)

                    val exerciseTypeString = intentBundle.getString(ModifyExerciseWorkoutActivity.EXERCISE_ACTIVITY_TYPE)
                    val exerciseType: WorkoutExerciseType = if (exerciseTypeString == null) WorkoutExerciseType.OTHER
                        else enumValueOf(exerciseTypeString)

                    adapter.modifyWorkoutExerciseInList(WorkoutExercise(id, name, numberOfReps, numberOfSets, weight, exerciseType), position)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addButton.setOnClickListener {
            logd("Pressed the add button")
            val intent = Intent(this, AddExerciseWorkoutActivity::class.java)
            addWorkoutExerciseLauncher.launch(intent)
        }

        binding.workoutExercises.adapter = adapter
        adapter.setWorkoutExercisesList(workoutExerciseRepository.findAll())
    }

    inner class ExerciseWorkoutAdapter : RecyclerView.Adapter<ExerciseWorkoutViewHolder>() {

        private var workoutExercises = mutableListOf<WorkoutExercise>()

        @SuppressWarnings("NotifyDataSetChanged")
        fun setWorkoutExercisesList(workoutExercises: List<WorkoutExercise>) {
            this.workoutExercises = workoutExercises.toMutableList()
            notifyDataSetChanged()
        }

        @SuppressWarnings("NotifyDataSetChanged")
        fun addWorkoutExerciseToList(workoutExercise: WorkoutExercise) {
            this.workoutExercises.add(workoutExercise)
            notifyDataSetChanged()
        }

        @SuppressWarnings("NotifyDataSetChanged")
        fun modifyWorkoutExerciseInList(workoutExercise: WorkoutExercise, position: Int) {
            this.workoutExercises[position] = workoutExercise
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseWorkoutViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = WorkoutExerciseBinding.inflate(inflater, parent, false)
            return ExerciseWorkoutViewHolder(binding)
        }

        @SuppressWarnings("NotifyDataSetChanged")
        override fun onBindViewHolder(holder: ExerciseWorkoutViewHolder, position: Int) {
            val workoutExercise = workoutExercises[position]
            logd("Workout Exercise's ID = ${workoutExercise.id}")

            holder.binding.workoutExerciseName.text = workoutExercise.name
            holder.binding.workoutExerciseNumberOfSets.text = workoutExercise.numberOfSets.toString()
            holder.binding.exerciseWorkoutNumberOfReps.text = workoutExercise.numberOfReps.toString()
            holder.binding.workoutExerciseWeight.text = workoutExercise.weight.toString()
            holder.binding.exerciseWorkoutType.text = workoutExercise.exerciseType.toString()
            holder.binding.deleteButton.setOnClickListener {
                logd("Pressed on deleted for $position")
                workoutExercises.removeAt(position)
                workoutExerciseRepository.remove(position)
                notifyDataSetChanged()
            }
            holder.binding.modifyButton.setOnClickListener {
                logd("Pressed on modify for $position")
                val intent = Intent(this@MainActivity, ModifyExerciseWorkoutActivity::class.java)
                intent.putExtra(ModifyExerciseWorkoutActivity.EXERCISE_ACTIVITY_POSITION, position)
                intent.putExtra(ModifyExerciseWorkoutActivity.EXERCISE_ACTIVITY_ID, workoutExercise.id)
                intent.putExtra(ModifyExerciseWorkoutActivity.EXERCISE_ACTIVITY_NAME, workoutExercise.name)
                intent.putExtra(ModifyExerciseWorkoutActivity.EXERCISE_ACTIVITY_NUMBER_OF_SETS, workoutExercise.numberOfSets)
                intent.putExtra(ModifyExerciseWorkoutActivity.EXERCISE_ACTIVITY_NUMBER_OF_REPS, workoutExercise.numberOfReps)
                intent.putExtra(ModifyExerciseWorkoutActivity.EXERCISE_ACTIVITY_WEIGHT, workoutExercise.weight)
                intent.putExtra(ModifyExerciseWorkoutActivity.EXERCISE_ACTIVITY_TYPE, workoutExercise.exerciseType.toString())
                modifyWorkoutExerciseLauncher.launch(intent)
            }
        }

        override fun getItemCount(): Int {
            return workoutExercises.size
        }
    }

    inner class ExerciseWorkoutViewHolder(val binding: WorkoutExerciseBinding) : RecyclerView.ViewHolder(binding.root)
}