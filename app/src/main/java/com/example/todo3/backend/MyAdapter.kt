package com.example.todo3.backend

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.todo3.MainActivity
import com.example.todo3.R
import kotlinx.android.synthetic.main.custom_row.view.*

class MyAdapter(val context:Context):RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    var items = mutableListOf<todos>()




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
                notifyDataSetChanged()



            }

        }


    }
    fun addTodo1 (todo: List<todos>){
        this.items = todo as MutableList<todos>
        notifyDataSetChanged()

    }

    fun deleteDone (){

        items.removeAll { item ->
            item.checkState
        }
        notifyDataSetChanged()

    }

    fun clearall(){

    }



    override fun getItemCount(): Int {
        return items.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }


}