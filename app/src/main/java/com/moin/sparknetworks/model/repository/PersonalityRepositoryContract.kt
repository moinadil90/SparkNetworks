package com.moin.sparknetworks.model.repository

import com.moin.sparknetworks.model.storage.records.QuestionRecord
import io.reactivex.Completable
import io.reactivex.Single
import org.json.JSONObject

interface PersonalityRepositoryContract {

    fun save(personalityObject: JSONObject): Completable

    fun fetchQuestionsFromDB(): Single<List<QuestionRecord>>
}