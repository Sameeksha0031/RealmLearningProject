package com.example.mybankapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mybankapp.Model.Tasks
import com.example.mybankapp.Model.VariableHolder
import com.example.mybankapp.databinding.ActivityMainBinding
import io.realm.OrderedRealmCollection
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var myRecyclerViewAdapter: MyRecyclerViewAdapter
    lateinit var realmHelper : RealmHelper
    var variableHolder = VariableHolder()

    var task = Tasks()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Realm.init(applicationContext)
        val realmConfig = RealmConfiguration.Builder().allowWritesOnUiThread(true).build()
        Realm.setDefaultConfiguration(realmConfig)
        realmHelper = RealmHelper()
        updateRV()
        variableHolder.taskName = findViewById(R.id.editTextTextPersonName)
        Log.d("#Sameeksha","onCreate variableHolder.taskName?.text = ${variableHolder.taskName}")
        binding.insertButton.setOnClickListener {

            if(binding.editTextTextPersonName.text.isNotEmpty()) {
                Log.d(
                    "#Sameeksha",
                    "binding.editTextTextPersonName = ${binding.editTextTextPersonName.text.toString()}"
                )
                task.let { it1 ->
                    realmHelper.insertData(it1.getValue(binding.editTextTextPersonName.text.toString()))
                    Log.d("#Sameeksha", "it1 = $it1")
                    Log.d(
                        "#Sameeksha",
                        "it1 = ${it1.getValue(binding.editTextTextPersonName.text.toString())}"
                    )
                }
                updateRV()
                Toast.makeText(this, "Task Inserted", Toast.LENGTH_SHORT).show()
            }
        }

        binding.updateButton.setOnClickListener {
            lifecycleScope.launch {
                variableHolder.edit?.let { it1 -> realmHelper.updateData(it1,binding.editTextTextPersonName.text.toString()) }
            }
            binding.editTextTextPersonName.setText("")
            updateRV()
        }

    }

    fun updateRV() {
        val realmResults: OrderedRealmCollection<Tasks> = realmHelper.getData() as OrderedRealmCollection<Tasks>
        myRecyclerViewAdapter = MyRecyclerViewAdapter(realmResults)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = myRecyclerViewAdapter
        myRecyclerViewAdapter.getVariableHolder(variableHolder)
    }
}