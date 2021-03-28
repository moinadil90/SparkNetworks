package com.moin.sparknetworks.model.storage

import com.moin.sparknetworks.model.storage.records.QuestionRecord
import io.reactivex.Completable
import io.reactivex.Single
import org.json.JSONObject

interface PersonalityStorageContract {

    /**
     * Subscribe to it to save personalityObject to RealmDB.
     *
     * @param personalityObject object which has questions and categories,
     * @return A {@Link Completable} to indicate whether this request is completed or not
     */
    fun save(personalityObject: JSONObject): Completable

    /**
     * Subscribe to it in order to get the List of [QuestionRecord].
     *
     * @return A [Single] emitting the list of [QuestionRecord].
     */
    fun fetchQuestionsFromDB(): Single<List<QuestionRecord>>
}