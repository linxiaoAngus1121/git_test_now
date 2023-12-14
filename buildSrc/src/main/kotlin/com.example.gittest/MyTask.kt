package com.example.gittest

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

/**
 * 请描述类的作用
 *
 * @author linxiao date: 2023年12月13日, 0013 10:30
 */
open class MyTask @Inject constructor(dependsOnTaskName: String) : DefaultTask() {
    private val dependsOnTaskName: String
    @TaskAction
    fun doTaskAction() {
        println(">>>>> Greeting from buildSrc com.example.gittest.MyTask")
        for (task in project.tasks) {
            println(">>>>> Greeting from buildSrc com.example.gittest.MyTask" + task.name)
        }
        val byType = project.extensions.getByType(MyPluginExtension::class.java)
        println(">>>>> extension.host = " + byType.host)
        println(">>>>> extension.port = " + byType.port)
    }

    init {
        group = "myTaskGroup"
        this.dependsOnTaskName = dependsOnTaskName
        dependsOn(dependsOnTaskName)
    }
}