package com.example.mybankapp

import android.content.Context
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.mybankapp.Model.Tasks
import com.example.mybankapp.Model.VariableHolder
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import io.realm.RealmResults

class MyRecyclerViewAdapter(realmResults: OrderedRealmCollection<Tasks>, autoUpdate : Boolean = true) :
    RealmRecyclerViewAdapter<Tasks,MyRecyclerViewAdapter.MyViewHolder>(realmResults, autoUpdate)  {

    lateinit var variableHolder : VariableHolder
    class MyViewHolder(itemView : View) :  RecyclerView.ViewHolder(itemView){
        var textViewOne = itemView.findViewById<TextView>(R.id.textViewOne)
        var textViewTwo = itemView.findViewById<TextView>(R.id.textViewTwo)
        var container = itemView.findViewById<ConstraintLayout>(R.id.container)
    }

    fun getVariableHolder(variable: VariableHolder) {
        variableHolder = variable
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_layout,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val tasks : Tasks? = getItem(position)
        holder.textViewOne.text = tasks?.task_id.toString()
        holder.textViewTwo.text = tasks?.task_name.toString()
        holder.textViewTwo.setOnClickListener {
            variableHolder.taskName.setText(getItem(position)?.task_name)
            variableHolder.edit = tasks?.task_id
            Log.d("#Sameeksha","VariableHolder().edit = ${tasks?.task_id} ${variableHolder.edit}")
            Log.d("#Sameeksha","variableHolder.taskName?.text = ${variableHolder.taskName?.text}")
        }

        holder.textViewTwo.setOnLongClickListener(object : OnLongClickListener {
            override fun onLongClick(p0: View?): Boolean {
                val realmHelper = RealmHelper()
                if (tasks != null) {
                    tasks.task_id?.let { realmHelper.deleteData(it) }
                }
                return true
            }

        })
    }

    override fun getItemId(position: Int): Long {
        return getItem(position)?.task_id!!.toLong()
    }
}

