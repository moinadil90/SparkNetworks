package com.moin.sparknetworks.domain

import android.content.Context
import com.moin.sparknetworks.annotations.Generated
import com.moin.sparknetworks.di.DaggerQuestionComponent
import com.moin.sparknetworks.di.QuestionModule
import com.moin.sparknetworks.model.repository.PersonalityRepositoryContract
import com.moin.sparknetworks.model.storage.records.QuestionRecord
import com.moin.sparknetworks.presentation.view.PersonalityActivity
import io.reactivex.Completable
import io.reactivex.Single
import org.json.JSONObject
import javax.inject.Inject

class PersonalityInteractor : PersonalityInteractorContract {

    @Inject
    lateinit var personalityRepository: PersonalityRepositoryContract

    @Generated
    constructor(context: Context, activity: PersonalityActivity) {
        DaggerQuestionComponent.builder()
            .questionModule(QuestionModule(context, activity))
            .build().inject(this)
    }

    constructor(personalityRepository: PersonalityRepositoryContract) {
        this.personalityRepository = personalityRepository
    }

    override fun save(personalityObject: JSONObject): Completable = personalityRepository.save(personalityObject)

    override fun fetchQuestionsFromDB(): Single<List<QuestionRecord>> {
        return  personalityRepository.fetchQuestionsFromDB()
    }
}