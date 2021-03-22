package com.moin.sparknetworks.model.storage

import com.moin.sparknetworks.model.storage.dao.PersonalityDAOContract
import com.moin.sparknetworks.model.storage.records.QuestionRecord
import io.reactivex.Completable
import io.reactivex.Single
import org.json.JSONObject

class PersonalityStorage(private val personalityDAO: PersonalityDAOContract): PersonalityStorageContract {

    override fun save(personalityObject: JSONObject): Completable {
        return personalityDAO.save(personalityObject)
    }

    override fun fetchQuestionsFromDB(): Single<List<QuestionRecord>> {
        return personalityDAO.getQuestions()
    }
}