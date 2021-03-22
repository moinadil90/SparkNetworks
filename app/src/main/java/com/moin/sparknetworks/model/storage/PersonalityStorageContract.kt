package com.moin.sparknetworks.model.storage

import com.moin.sparknetworks.model.storage.records.QuestionRecord
import io.reactivex.Completable
import io.reactivex.Single
import org.json.JSONObject

interface PersonalityStorageContract {

    fun save(personalityObject: JSONObject): Completable

    fun fetchQuestionsFromDB(): Single<List<QuestionRecord>>
}