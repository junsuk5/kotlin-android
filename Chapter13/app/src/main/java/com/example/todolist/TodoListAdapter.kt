package com.example.todolist

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.realm.OrderedRealmCollection
import io.realm.RealmBaseAdapter

class TodoListAdapter(realmResult: OrderedRealmCollection<Todo>) : RealmBaseAdapter<Todo>(realmResult) {

    class TodoViewHolder(view: View) {
        val dateTextView: TextView = view.findViewById(R.id.text1)
        val textTextView: TextView = view.findViewById(R.id.text2)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val vh: TodoViewHolder
        val view: View

        if (convertView == null) {
            view = LayoutInflater.from(parent?.context)
                    .inflate(R.layout.item_todo, parent, false)

            vh = TodoViewHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as TodoViewHolder
        }

        adapterData?.let {
            val item = it[position]
            vh.textTextView.text = item.title
            vh.dateTextView.text = DateFormat.format("yyyy/MM/dd", item.date)
        }

        return view
    }

    override fun getItemId(position: Int): Long {
        adapterData?.let {
            return it[position].id
        }
        return super.getItemId(position)
    }
}

