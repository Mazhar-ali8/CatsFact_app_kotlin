package com.catbreedapp.catbreedsapp

import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.catbreedapp.catbreedsapp.model.CatName
import com.catbreedapp.catbreedsapp.utils.BUTTON_ONE_CLICKED
import com.catbreedapp.catbreedsapp.utils.BUTTON_ZERO_CLICKED

class SharedViewModel : ViewModel() {
    var selectedYear: String? = null
    var selectedCatName: ObservableField<String> = ObservableField(String())
    val clickEvents: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }

    var yearSelectListener: AdapterView.OnItemSelectedListener =
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val item: Any = parent.getItemAtPosition(position).toString()
                selectedYear = item.toString()
                selectedCatName.set(selectedYear.toString())
                Log.e("TAG", "onItemSelected: ${selectedCatName.get()}")
                clickEvents.value = BUTTON_ONE_CLICKED

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }





}