package com.moin.sparknetworks.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.moin.sparknetworks.domain.PersonalityInteractor
import com.moin.sparknetworks.lib_persistencestorage.PersistenceStorage
import com.moin.sparknetworks.model.repository.PersonalityRepository
import com.moin.sparknetworks.model.repository.PersonalityRepositoryContract
import com.moin.sparknetworks.model.storage.PersonalityStorage
import com.moin.sparknetworks.model.storage.PersonalityStorageContract
import com.moin.sparknetworks.model.storage.dao.PersonalityDAO
import com.moin.sparknetworks.model.storage.dao.PersonalityDAOContract
import com.moin.sparknetworks.presentation.view.PersonalityActivity
import com.moin.sparknetworks.presentation.viewmodel.PersonalityViewModel
import com.moin.sparknetworks.presentation.viewmodel.PersonalityViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class QuestionModule(private val context: Context, private var activity: PersonalityActivity) {

    @Provides
    fun providesPersonalityViewModel(personalityInteractor: PersonalityInteractor): PersonalityViewModel =
        ViewModelProvider(activity, PersonalityViewModelFactory(personalityInteractor)).get(
            PersonalityViewModel::class.java
        )

    @Provides
    fun providesPersonalityRepository(): PersonalityRepositoryContract =
        PersonalityRepository(context, activity)

    @Provides
    fun providesPersonalityInteractor(): PersonalityInteractor =
        PersonalityInteractor(context, activity)

    @Provides
    fun providesPersonalityStorage(personalityDAO: PersonalityDAOContract): PersonalityStorageContract =
        PersonalityStorage(personalityDAO)

    @Provides
    fun providesPersonalityDAO(): PersonalityDAOContract = PersonalityDAO(PersistenceStorage)

}