package com.example.banktrack.ui.screens

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.example.banktrack.CustomArrayAdapter
import com.example.banktrack.R
import com.example.banktrack.databinding.EnterTagDetailsBinding

class EnterTagDetails : AppCompatActivity() {

    private lateinit var binding: EnterTagDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EnterTagDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBankDropdown()
    }

    private fun setupBankDropdown() {
        val banksArray = resources.getStringArray(R.array.banks)
        val adapter = CustomArrayAdapter(this, R.layout.spinner_items, banksArray)

        binding.bankNameSpinner.adapter = adapter

        binding.bankNameSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    adapter.setSelectedPosition(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // No action needed
                }
            }
    }
}
