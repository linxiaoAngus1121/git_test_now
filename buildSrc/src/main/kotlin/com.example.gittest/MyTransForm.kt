package com.example.gittest

import org.apache.tools.ant.taskdefs.Transform
import org.gradle.api.artifacts.transform.TransformAction
import org.gradle.api.artifacts.transform.TransformOutputs
import org.gradle.api.artifacts.transform.TransformParameters
import org.gradle.internal.classpath.CachedClasspathTransformer

/**
 *
 * 请描述类的作用
 * @author linxiao date: 2023年12月13日, 0013 16:56
 */
class MyTransForm: TransformAction<TransformParameters.None> {
    override fun getParameters(): TransformParameters.None {
        TODO("Not yet implemented")
    }

    override fun transform(outputs: TransformOutputs) {
        TODO("Not yet implemented")
    }
}