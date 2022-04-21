package com.example.todo3

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo3.adapters.MyAdapter
import com.example.todo3.adapters.todos
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog1.*

class MainActivity : AppCompatActivity() {
    //initializing variables outside the functions to be able to use them inside
    private var list= mutableListOf<todos>()
    private val adapter = MyAdapter(this,list)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //set the layout manager
        recyclerxml.layoutManager= LinearLayoutManager(this)
        //initialize adapter class and pass list as param

        //set adapter instance to recyclerview
        recyclerxml.adapter= adapter

        val swipetodelete = object :swipeToDelete(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val deletedtodo = list.get(position)

                list.removeAt(position)
                adapter.notifyItemRemoved(position)
                Snackbar.make(recyclerxml,"Are you sure you want to delete this todo?",Snackbar.LENGTH_LONG).setAction("undo", View.OnClickListener {
                    list.add(position,deletedtodo)
                    adapter.notifyItemInserted(position)
                }).show()

            }
        }
        val itemTouchHelper = ItemTouchHelper(swipetodelete)

        itemTouchHelper.attachToRecyclerView(recyclerxml)

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
                val todo = todos(itemtext)
                adapter.addTodo1(todo)
            }
            editText.hideKeyboard()
            mDialog.dismiss()

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


    private fun getTodo():String{


        val text:String = editTxt.text.toString()

        editTxt.setText("")
       return text
    }

    fun cleardone(view: View) {
        if (list.isNotEmpty()){
            adapter.deleteDone()
            Toast.makeText(this, "done todos deleted !", Toast.LENGTH_SHORT).show()
        }
        else Toast.makeText(this, "view is already cleared !", Toast.LENGTH_SHORT).show()

    }


}