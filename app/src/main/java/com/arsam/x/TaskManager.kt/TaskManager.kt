package com.arsam.x

data class AssistantTask(
    val id: Long,
    val title: String,
    val steps: List<String>,
    var currentStep: Int = 0,
    var completed: Boolean = false
)

object TaskManager {

    private val tasks = mutableListOf<AssistantTask>()

    fun createTask(
        title: String,
        steps: List<String>
    ): AssistantTask {
        val task = AssistantTask(
            id = System.currentTimeMillis(),
            title = title,
            steps = steps
        )

        tasks.add(task)
        return task
    }

    fun getCurrentStep(task: AssistantTask): String? {
        if (task.completed) return null
        return task.steps.getOrNull(task.currentStep)
    }

    fun completeCurrentStep(task: AssistantTask): Boolean {
        if (task.completed) return false

        task.currentStep++

        if (task.currentStep >= task.steps.size) {
            task.completed = true
        }

        return true
    }

    fun getAllTasks(): List<AssistantTask> {
        return tasks.toList()
    }

    fun removeTask(task: AssistantTask) {
        tasks.remove(task)
    }
}
