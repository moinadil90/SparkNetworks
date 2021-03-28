package com.moin.sparknetworks.model.storage.dao

import com.moin.sparknetworks.lib_persistencestorage.PersistenceStorageContract
import com.moin.sparknetworks.model.storage.records.QuestionRecord
import io.reactivex.Completable
import io.reactivex.Single
import io.realm.Realm
import io.realm.internal.IOException
import org.json.JSONObject
import timber.log.Timber

class PersonalityDAO(private val persistenceStorage: PersistenceStorageContract) :
    PersonalityDAOContract {

    override fun save(
        personalityObject: JSONObject
    ): Completable = Completable.create { emitter ->
        getRealm().run {
            try {
                beginTransaction()
                createAllFromJson(
                    QuestionRecord::class.java,
                    personalityObject.getJSONArray("questions")
                )
                commitTransaction()
                emitter.onComplete()
            } catch (e: Exception) {
                Timber.e(e)
                if (isInTransaction) cancelTransaction()
                emitter.onError(e)
            }
        }
    }

    override fun getQuestions(): Single<List<QuestionRecord>> = Single.create { emitter ->
        try {
            emitter.onSuccess(getQuestionsQuery().findAll())
        } catch (e: Exception) {
            Timber.d(e)
            emitter.onError(IOException("No record found."))
        }
    }

    private fun getQuestionsQuery() = getRealm().where(QuestionRecord::class.java)
    private fun getRealm(): Realm {
        return (persistenceStorage.getDB() as Realm)
    }

}