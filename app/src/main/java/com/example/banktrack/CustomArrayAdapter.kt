package com.example.banktrack

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat

class CustomArrayAdapter(
    context: Context,
    private val resource: Int,
    private val items: Array<String>
) : ArrayAdapter<String>(context, resource, items) {

    private var selectedPosition = -1

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)
        val textView = view.findViewById<TextView>(R.id.spinner_text)
        val checkmark = view.findViewById<ImageView>(R.id.checkmark)
        textView.text = getItem(position)

        if (position == selectedPosition) {
            checkmark.visibility = View.VISIBLE

        } else {
            checkmark.visibility = View.GONE
            view.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
        }
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)
        val textView = view.findViewById<TextView>(R.id.spinner_text)
        val checkmark = view.findViewById<ImageView>(R.id.checkmark)
        textView.text = getItem(position)

        if (position == selectedPosition) {
            checkmark.visibility = View.VISIBLE
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.orange))
        } else {
            checkmark.visibility = View.GONE
            view.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
        }
        return view
    }

    fun setSelectedPosition(position: Int) {
        selectedPosition = position
        notifyDataSetChanged()

    }
}
