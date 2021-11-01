package com.example.laborator2_ma.viewmodel

//class ExerciseWorkoutAdapter : RecyclerView.Adapter<ExerciseWorkoutViewHolder>() {
//
//    private var workoutExercises = mutableListOf<WorkoutExercise>()
//
//    @SuppressWarnings("NotifyDataSetChanged")
//    fun setWorkoutExercisesList(workoutExercises: List<WorkoutExercise>) {
//        this.workoutExercises = workoutExercises.toMutableList()
//        notifyDataSetChanged()
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseWorkoutViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        val binding = WorkoutExerciseBinding.inflate(inflater, parent, false)
//        return ExerciseWorkoutViewHolder(binding)
//    }
//
//    @SuppressWarnings("NotifyDataSetChanged")
//    override fun onBindViewHolder(holder: ExerciseWorkoutViewHolder, position: Int) {
//        val workoutExercise = workoutExercises[position]
//        holder.binding.workoutExerciseName.text = workoutExercise.name
//        holder.binding.workoutExerciseNumberOfSets.text = workoutExercise.numberOfSets.toString()
//        holder.binding.exerciseWorkoutNumberOfReps.text = workoutExercise.numberOfReps.toString()
//        holder.binding.workoutExerciseWeight.text = workoutExercise.weight.toString()
//        holder.binding.exerciseWorkoutType.text = workoutExercise.exerciseType.toString()
//        holder.binding.deleteButton.setOnClickListener {
//            logd("Pressed on deleted for $position")
//            workoutExercises.removeAt(position)
//            notifyDataSetChanged()
//        }
//        holder.binding.modifyButton.setOnClickListener {
//            logd("Pressed on modify for ${position}")
//
//            val intent = Intent(it.context, ExerciseWorkoutActivity::class.java)
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return workoutExercises.size
//    }
//}
//
//class ExerciseWorkoutViewHolder(val binding: WorkoutExerciseBinding) : RecyclerView.ViewHolder(binding.root)