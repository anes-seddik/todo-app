package com.example.todo3

import android.content.Context
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo3.adapters.MyAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog1.*

class MainActivity : AppCompatActivity() {
    //initializing variables outside the functions to be able to use them inside
    private var list= ArrayList<String>()
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



    fun addtodo(view: View) {


        val  mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog1,null)
        val mBuilder = AlertDialog.Builder(this)
        mBuilder.setView(mDialogView)
        val mDialog = mBuilder.create()
        mDialog.show()
        mDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        mDialog.dilgaddbtn.setOnClickListener {

            val item:String = mDialog.editTxt.text.toString()

            mDialog.editTxt.setText("")
            if (item!=""){
                list.add(item)
                adapter.notifyItemInserted(adapter.items.size-1)
            }
            mDialog.dismiss()
        }

    }




    private fun getTodo():String{


        val text:String = editTxt.text.toString()

        editTxt.setText("")
       return text
    }

    fun clearall(view: View) {
        if (list.isNotEmpty()){
            list.clear()
            adapter.notifyDataSetChanged()
            Toast.makeText(this, "view cleared !", Toast.LENGTH_SHORT).show()
        }
        else Toast.makeText(this, "view is already cleared !", Toast.LENGTH_SHORT).show()

    }


}