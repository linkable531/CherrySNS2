package com.example.cherry.message

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.cherry.R
import com.example.cherry.auth.UserDataModel

class MsgAdapter(val context: Context, val items : MutableList<MsgModel>) : BaseAdapter() {
    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(p0: Int): Any {
        return items[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    //connect view
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        //item connect
        var convertView = convertView
        if (convertView == null) {
            convertView = LayoutInflater.from(parent?.context).inflate(R.layout.list_view_item, parent, false)
        }

        //can see liker's name in listview
        val name = convertView!!.findViewById<TextView>(R.id.name)
            name.text=items[position].senderInfo

        //can see liker's age in listview
        val text=convertView!!.findViewById<TextView>(R.id.age)
            text.text=items[position].sendTxt

        return convertView!!
    }
}