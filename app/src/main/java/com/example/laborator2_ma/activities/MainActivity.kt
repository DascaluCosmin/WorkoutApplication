package com.example.laborator2_ma.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import com.example.laborator2_ma.databinding.ActivityMainBinding
import com.example.laborator2_ma.databinding.WorkoutSetBinding
import com.example.laborator2_ma.dependencyinjection.ApplicationContainer
import com.example.laborator2_ma.domain.WorkoutSet
import com.example.laborator2_ma.utils.logd
import com.example.laborator2_ma.utils.toast
import java.time.LocalDate

class MainActivity : AppCompatActivity() {

    companion object {
        const val MAIN_ACTIVITY_WORKOUT_SET_ID = "id"
        const val MAIN_ACTIVITY_WORKOUT_SET_NAME = "name"
        const val MAIN_ACTIVITY_WORKOUT_SET_DATE = "date"
        const val MAIN_ACTIVITY_CREATE_WORKOUT = "Create Workout"
    }

    private lateinit var binding: ActivityMainBinding
    private val adapter = WorkoutSetAdapter()

    private val addWorkoutSetLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        logd("Add WorkoutSet response: ${result.resultCode}")
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            if (intent != null) {
                val intentBundle = intent.extras
                if (intentBundle != null) {
                    val id = intentBundle.getInt(MAIN_ACTIVITY_WORKOUT_SET_ID)
                    var name = intentBundle.getString(MAIN_ACTIVITY_WORKOUT_SET_NAME)
                    if (name == null) {
                        name = "Unknown name"
                    }
                    val createdAt = LocalDate.parse(intentBundle.getString(MAIN_ACTIVITY_WORKOUT_SET_DATE))

                    adapter.addWorkoutSetToList(WorkoutSet(id, name, createdAt, ArrayList()))
                }
            }
        }
    }

    private val detailsWorkoutSetLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        logd("Back to all workout sets: ${result.resultCode}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonAddWorkoutSet.setOnClickListener {
            val intent = Intent(this, WorkoutExerciseActivity::class.java)
            intent.putExtra(MAIN_ACTIVITY_CREATE_WORKOUT, true)
            addWorkoutSetLauncher.launch(intent)
        }

        binding.workoutSets.adapter = adapter
        adapter.setWorkoutSetsList(ApplicationContainer.workoutSetRepository.findAll())
    }

    inner class WorkoutSetAdapter : RecyclerView.Adapter<WorkoutSetViewHolder>() {

        private var workoutSets = mutableListOf<WorkoutSet>()

        @SuppressWarnings("NotifyDataSetChanged")
        fun addWorkoutSetToList(workoutSet: WorkoutSet) {
            this.workoutSets.add(workoutSet)
            notifyDataSetChanged()
        }

        @SuppressWarnings("NotifyDataSetChanged")
        fun setWorkoutSetsList(workoutSets: List<WorkoutSet>) {
            this.workoutSets = workoutSets.toMutableList()
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutSetViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = WorkoutSetBinding.inflate(inflater, parent,false)
            return WorkoutSetViewHolder(binding)
        }

        @SuppressWarnings("NotifyDataSetChanged")
        override fun onBindViewHolder(holder: WorkoutSetViewHolder, position: Int) {
            val workoutSet = workoutSets[position]
            logd("Workout Set's ID = ${workoutSet.id}")

            holder.binding.workoutSetName.text = workoutSet.name
            holder.binding.workoutSetDate.text = workoutSet.createdAt.toString()
            holder.binding.workoutSetDeleteButton.setOnClickListener {
                logd("Pressed on delete for position $position")
                workoutSets.removeAt(position)
                ApplicationContainer.workoutSetRepository.remove(position)
                notifyDataSetChanged()
                toast("Workout Set deleted successfully")
            }
            holder.binding.workoutSetCardView.setOnClickListener {
                logd("Pressed on card view for position $position")
                val intent = Intent(this@MainActivity, WorkoutExerciseActivity::class.java)
                intent.putExtra(MAIN_ACTIVITY_WORKOUT_SET_ID, position)
                intent.putExtra(MAIN_ACTIVITY_CREATE_WORKOUT, false)

                detailsWorkoutSetLauncher.launch(intent)
            }
        }

        override fun getItemCount(): Int {
            return workoutSets.size
        }
    }

    inner class WorkoutSetViewHolder(val binding: WorkoutSetBinding): RecyclerView.ViewHolder(binding.root)
}