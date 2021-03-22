package com.moin.sparknetworks.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moin.sparknetworks.domain.PersonalityInteractorContract

class PersonalityViewModelFactory(private val personalityInteractor: PersonalityInteractorContract) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PersonalityViewModel::class.java)) {
            return PersonalityViewModel(personalityInteractor) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}