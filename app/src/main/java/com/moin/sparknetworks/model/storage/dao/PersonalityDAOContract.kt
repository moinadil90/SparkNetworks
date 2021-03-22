package com.moin.sparknetworks.model.storage.dao

import com.moin.sparknetworks.model.storage.records.QuestionRecord
import io.reactivex.Completable
import io.reactivex.Single
import org.json.JSONObject

interface PersonalityDAOContract {

    fun save(
        personalityObject: JSONObject
    ): Completable

    fun getQuestions(): Single<List<QuestionRecord>>
}