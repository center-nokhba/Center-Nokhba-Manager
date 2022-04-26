package com.nokhba.center.panel.ui.formation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FormationViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is add Fragment"
    }
    val text: LiveData<String> = _text
}