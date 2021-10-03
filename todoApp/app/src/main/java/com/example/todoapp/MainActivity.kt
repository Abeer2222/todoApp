package com.example.todoapp

import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var items: ArrayList<ToDoItem>
    private lateinit var rvAdapter: RVAdapter
    private lateinit var rvItems: RecyclerView
    private lateinit var floatBt: FloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "To Do App"
        items = arrayListOf()
        rvItems = findViewById<RecyclerView>(R.id.rvItems)
        rvAdapter = RVAdapter(items)
        rvItems.adapter = rvAdapter
        rvItems.layoutManager = LinearLayoutManager(this)

        floatBt = findViewById(R.id.fabAdd)
        floatBt.setOnClickListener {
            customAlertDialog()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var itemsDeleted = 0
        for (i in items) {
            if (i.checked) {
                itemsDeleted++
            }
        }

        if (itemsDeleted > 0) {
            Toast.makeText(this, "$itemsDeleted items deleted", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "No items selected", Toast.LENGTH_LONG).show()
        }
        rvAdapter.deleteItems()
        return super.onOptionsItemSelected(item)
    }


    private fun customAlertDialog() {
        val dialogBuilder = AlertDialog.Builder(this)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL

        val toDo = EditText(this)
        toDo.hint = "Add a new task "
        layout.addView(toDo)

        dialogBuilder.setPositiveButton("ADD",
            DialogInterface.OnClickListener { dialog, id ->
            items.add(ToDoItem(toDo.text.toString()))
        })
            .setNegativeButton("CANCEL",
                DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })

        val alert = dialogBuilder.create()
        alert.setTitle("New Item")
        alert.setView(layout)
        alert.show()
    }
}

