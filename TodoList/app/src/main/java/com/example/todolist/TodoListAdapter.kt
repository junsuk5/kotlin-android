package com.example.todolist

import android.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.realm.OrderedRealmCollection
import io.realm.RealmBaseAdapter
import java.text.SimpleDateFormat
import java.util.*

class TodoListAdapter(realmResult: OrderedRealmCollection<Todo>) : RealmBaseAdapter<Todo>(realmResult) {
    val sdf = SimpleDateFormat("YYYY/MM/dd", Locale.KOREA)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val vh: ViewHolder
        val view: View?
        if (convertView == null) {
            view = LayoutInflater.from(parent?.context)
                    .inflate(R.layout.simple_list_item_2, parent, false)

            vh = ViewHolder()
            vh.dateTextView = view.findViewById(android.R.id.text1)
            vh.textTextView = view.findViewById(android.R.id.text2)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ViewHolder
        }

        if (adapterData != null) {
            val item = adapterData!![position]
            vh.textTextView.text = item.text
            vh.dateTextView.text = sdf.format(item.date)
        }

        return view
    }

    override fun getItemId(position: Int): Long {
        if (adapterData != null) {
            return adapterData!![position].id
        }
        return super.getItemId(position)
    }
}

class ViewHolder() {
    lateinit var dateTextView: TextView
    lateinit var textTextView: TextView
}