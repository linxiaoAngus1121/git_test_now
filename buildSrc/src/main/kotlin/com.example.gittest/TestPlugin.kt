package com.example.gittest

import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File
import java.io.IOException
import java.util.*

/**
 * 请描述类的作用
 *
 * @author linxiao date: 2023年12月13日, 0013 10:11
 */
open class TestPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        println(">>>>> Greeting from buildSrc MyPlugin")
        //
        val create = target.extensions.create("myExtensionInfo", MyPluginExtension::class.java)
        println("parent---" + target.name)
        println("getRootDir---" + target.projectDir.name)

        val androidComponents = target.extensions.getByType(AndroidComponentsExtension::class.java)
        val file = File(target.projectDir, "aa.xml")
        try {
            file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val properties = Properties()
        val file1 = target.rootProject.file("local.properties")
        properties.load(file1.bufferedReader())
        val property = properties.getProperty("isOpen")
        println(">>>>> fdgfde = $property")
        target.afterEvaluate {
            it.tasks.forEach { task->
                val name = task.name
                if (name.startsWith("assemble") && name.endsWith("Release")) {
                    val replace = name.replace("assemble", "publish")
                    println(">>>>> 匹配到任务 = $replace")
                    target.tasks.register(replace, MyTask::class.java, "assembleRelease")
                }
            }
        }
    }
}