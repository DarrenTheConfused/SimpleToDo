package com.example.simpletodo20

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import android.os.FileUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils.readLines
import org.apache.commons.io.FileUtils.writeLines
import org.apache.commons.io.IOUtils.writeLines
import java.io.File
import java.io.IOException
import java.nio.charset.Charset
import org.apache.commons.io.FileUtils;


class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                //remove item
                listOfTasks.removeAt(position)
                //notify
                adapter.notifyDataSetChanged()

                saveItems()
            }
        }

        // Let's detect when the user clicks on the add button
        //findViewById<Button>(R.id.button).setOnClickListener{
            //Code executed when user clicks on button
          //  Log.i("Caren", "User clicked on button")
        //}

        loadItems()

        //look at recycler in layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //Set up Button for user to add tasks

        val inputTextField = findViewById<EditText>(R.id.addTaskField)

        //Get reference and set on click
        findViewById<Button>(R.id.button).setOnClickListener{
            //grab text
            val userInputtedTask = inputTextField.text.toString()

            //add string to list of tasks
            listOfTasks.add(userInputtedTask)

            //Notify
            adapter.notifyItemInserted(listOfTasks.size-1)


            //clear field for new task
            inputTextField.setText("")

            saveItems()
        }
    }


    //Save data by reading and writing

    //get file
    fun getDataFile() : File {
        //line = task
        return File(filesDir, "data.txt")
    }
    //load
    fun loadItems(){
        try{
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch(ioException: IOException){
            ioException.printStackTrace()
        }
    }

    //save
    fun saveItems() {
        try{
            FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch (ioException: IOException){
            ioException.printStackTrace()
        }
    }



}