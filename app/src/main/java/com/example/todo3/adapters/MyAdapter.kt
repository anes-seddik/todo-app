package com.example.todo3.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todo3.R
import kotlinx.android.synthetic.main.custom_row.view.*

class MyAdapter(val context:Context, val items: ArrayList<String>):RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.custom_row,parent,false

            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items.get(position)
        holder.title.text=item


    }
    fun addTodo (todo: String){
        items.add(todo)
        notifyItemInserted(items.size-1)

    }

    override fun getItemCount(): Int {
        return items.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.todo_item

    }


}