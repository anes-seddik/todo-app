package com.example.todo3.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.todo3.R
import kotlinx.android.synthetic.main.custom_row.view.*

class MyAdapter(val context:Context, val items: MutableList<todos>):RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.custom_row,parent,false

            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = items[position]
        holder.itemView.apply {
            todo_item.text= item.todotitle
            checkbox.isChecked = item.checkState
            checkbox.setOnCheckedChangeListener { _, ischecked ->
                item.checkState = !item.checkState
            }

        }


    }
    fun addTodo1 (todo: todos){
        items.add(todo)
        notifyItemInserted(items.size-1)

    }

    fun deleteDone (){

        items.removeAll { item ->
            item.checkState
        }
        notifyDataSetChanged()

    }



    override fun getItemCount(): Int {
        return items.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }


}