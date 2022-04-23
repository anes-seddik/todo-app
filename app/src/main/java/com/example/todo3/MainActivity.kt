package com.example.todo3

import android.content.Context
import android.icu.text.Transliterator
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo3.backend.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_row.*
import kotlinx.android.synthetic.main.custom_row.view.*
import kotlinx.android.synthetic.main.dialog1.*

class MainActivity : AppCompatActivity() {
    //initializing variables outside the functions to be able to use them inside


    private lateinit var mViewmodel : todoViewmodel

    private val adapter = MyAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mViewmodel= ViewModelProvider(this).get(todoViewmodel::class.java)
        mViewmodel.allNotes.observe(this, Observer {todo ->
            adapter.addTodo1(todo)
        })

        //set the layout manager
        recyclerxml.layoutManager= LinearLayoutManager(this)
        //initialize adapter class and pass list as param

        //set adapter instance to recyclerview
        recyclerxml.adapter= adapter

        val swipetodelete = object : swipeToDelete(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val deletedtodo = adapter.items[position]
                mViewmodel.deleteNote(deletedtodo)
                Snackbar.make(recyclerxml,"Are you sure you want to delete this todo?",Snackbar.LENGTH_LONG).setAction("undo", View.OnClickListener {
                    mViewmodel.addNote(deletedtodo)
                }).show()

            }
        }
        val itemTouchHelper = ItemTouchHelper(swipetodelete)

        itemTouchHelper.attachToRecyclerView(recyclerxml)

    }

    fun update(viewHolder: RecyclerView.ViewHolder){
        val position = viewHolder.adapterPosition
        val item = adapter.items[position]
        R.layout.custom_row
        checkbox.setOnCheckedChangeListener { _, ischecked ->
            item.checkState = !item.checkState
            val newcheck = todos(item.todotitle,item.checkState)
            adapter.items[position]=newcheck
            adapter.notifyItemChanged(position)
            mViewmodel.updateNote(newcheck)
        }

    }



    fun addtask(view: View) {


        val  mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog1,null)
        val mBuilder = AlertDialog.Builder(this)
        mBuilder.setView(mDialogView)
        val mDialog = mBuilder.create()
        mDialog.show()

        val editText= mDialog.editTxt



        mDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        mDialog.dilgaddbtn.setOnClickListener {

            val itemtext:String = mDialog.editTxt.text.toString()

            mDialog.editTxt.setText("")
            if (itemtext!=""){
                insertToDatabase(itemtext)
            }
            editText.hideKeyboard()
            mDialog.dismiss()

        }

    }

    private fun insertToDatabase(todo: String) {
        if(todo.isNotEmpty()){
            val item = todos(todo)

            mViewmodel.addNote(item)
            Toast.makeText(this, "successfully added to DB", Toast.LENGTH_SHORT).show()
        }
    }

    fun View.showKeyboard() {
        this.requestFocus()
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    fun View.hideKeyboard() {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }




    fun cleardone(view: View) {
        if (adapter.items.isNotEmpty()){
            mViewmodel.deleteAllTodos()

            Toast.makeText(this, "todos deleted !", Toast.LENGTH_SHORT).show()
        }
        else Toast.makeText(this, "view is already cleared !", Toast.LENGTH_SHORT).show()

    }


}