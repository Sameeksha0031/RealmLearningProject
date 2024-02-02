package com.example.mybankapp

import com.example.mybankapp.Model.Tasks
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.where

class RealmHelper {
    val realm : Realm = Realm.getDefaultInstance()

    fun insertData(task: Tasks) { // insert data to realm
        var number = realm.where<Tasks>().max("task_id")
        if(number == null) {
            number = 0
        } else {
            number = number.toInt() + 1
        }

        task.task_id = number
        realm.beginTransaction()
        realm.copyToRealm(task)
        realm.commitTransaction()
    }

    fun getData() : List<Tasks> { //Select Query
        return realm.where<Tasks>().findAll()
    }

    suspend fun updateData(task_id : Int, task_name : String) { // Update Query
        var task : Tasks? = realm.where<Tasks>().equalTo("task_id",task_id).findFirst()
        realm.executeTransaction {
            if (task != null) {
                task.task_name = task_name
                it.copyFromRealm(task)
            }
        }
    }

    fun deleteData(task_id : Int) {
        var task : Tasks ?= realm.where<Tasks>().equalTo("task_id",task_id).findFirst()
        realm.executeTransaction {
            task?.deleteFromRealm()
        }
    }
}