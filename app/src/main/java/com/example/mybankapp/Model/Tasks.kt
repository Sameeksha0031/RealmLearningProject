package com.example.mybankapp.Model

import android.util.Log
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Tasks : RealmObject() {

    @PrimaryKey
    var task_id : Int ?= null
    var task_name : String ? = null

    fun getValue(name : String) : Tasks {
        val tasks = Tasks()
        tasks.task_name = name
        Log.d("#Sameeksha","getValue = $tasks")
        return tasks
    }

}