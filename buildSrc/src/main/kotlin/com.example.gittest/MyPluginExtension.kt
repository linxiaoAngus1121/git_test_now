package com.example.gittest

import groovy.lang.Closure
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.util.ConfigureUtil

/**
 * 请描述类的作用
 *
 * @author linxiao date: 2023年12月13日, 0013 10:49
 */
open class MyPluginExtension {
    @JvmField
    var host: String? = null
    @JvmField
    var port: String? = null


}