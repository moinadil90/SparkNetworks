package com.moin.sparknetworks.di

import com.moin.sparknetworks.domain.PersonalityInteractor
import com.moin.sparknetworks.model.repository.PersonalityRepository
import com.moin.sparknetworks.presentation.view.PersonalityActivity
import dagger.Component

@Component(
    modules = [
        QuestionModule::class
    ]
)
internal interface QuestionComponent {
    fun inject(activity: PersonalityActivity)
    fun inject(personalityRepository: PersonalityRepository)
    fun inject(personalityInteractor: PersonalityInteractor)
}
