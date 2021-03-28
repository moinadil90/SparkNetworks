package com.moin.sparknetworks.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.moin.sparknetworks.domain.PersonalityInteractorContract
import com.moin.sparknetworks.model.storage.records.QuestionRecord
import io.reactivex.Completable
import io.reactivex.Single
import org.json.JSONObject

class PersonalityViewModel(private val personalityInteractor: PersonalityInteractorContract) : ViewModel(), PersonalityViewModelContract {

    override fun save(personalityObject: JSONObject): Completable {
        return personalityInteractor.save(personalityObject)
    }

    override fun fetchQuestionsFromDB(): Single<List<QuestionRecord>> {
        return personalityInteractor.fetchQuestionsFromDB()
    }
}
