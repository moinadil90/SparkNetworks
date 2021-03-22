package com.moin.sparknetworks.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.moin.sparknetworks.PersistenceStorageManager
import com.moin.sparknetworks.R
import com.moin.sparknetworks.lib_persistencestorage.PersistenceStorage
import com.moin.sparknetworks.model.storage.records.QuestionRecord
import com.moin.sparknetworks.utils.readJSONFromAsset
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableSingleObserver
import io.realm.Realm
import kotlinx.android.synthetic.main.main_fragment.*
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber


class PersonalityFragment : Fragment() {

    private var realm: Realm = Realm.getDefaultInstance()
    private var questionsList = mutableListOf<JSONArray>()
    private var hardfactList = mutableListOf<QuestionRecord>()
    private var lifestyleList = mutableListOf<QuestionRecord>()
    private var introversionList = mutableListOf<QuestionRecord>()
    private var passionList = mutableListOf<QuestionRecord>()

    companion object {
        fun newInstance() = PersonalityFragment()
    }

    private var personalityAdapter: PersonalityAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        personalityAdapter = context?.let { PersonalityAdapter(it) }

        if (Realm.getDefaultInstance().where(QuestionRecord::class.java).findFirst() == null) {
            val personalityObject =
                JSONObject(readJSONFromAsset(activity?.applicationContext) ?: "")
            //questionsList.add(((personalityObject.get("questions") as JSONArray)))
            saveQuestionsToDB(personalityObject)
        }

        fetchQuestionsFromDB()

        item_rv.layoutManager = LinearLayoutManager(activity)
        item_rv.itemAnimator = DefaultItemAnimator()

        item_rv.adapter = personalityAdapter
    }

    private fun saveQuestionsToDB(personalityObject: JSONObject) {
        (activity as PersonalityActivity).viewModel.save(personalityObject)
            .subscribe(
                object : DisposableCompletableObserver() {
                    override fun onComplete() {
                        Timber.d("Success")
                    }

                    override fun onError(e: Throwable) {
                        Timber.e(e)
                    }
                })
    }

    private fun fetchQuestionsFromDB() {
        (activity as PersonalityActivity).viewModel.fetchQuestionsFromDB()
            .subscribe(object : DisposableSingleObserver<List<QuestionRecord>>() {

                override fun onSuccess(t: List<QuestionRecord>) {
                    println("Success: "+ t.get(0).question_type?.type)
                    personalityAdapter?.setData(t)
                }

                override fun onError(e: Throwable) {
                    Timber.d(e)
                }
            })
    }


    /*private fun convertQuestionListToQuestionRecord(questionsList: MutableList<JSONArray>): List<QuestionRecord> {
        val questions = mutableListOf<QuestionRecord>()
        for (i in 0..(questionsList[0].length().minus(1))) {
            questions.add(
                QuestionRecord(
                    ((questionsList[0][i] as JSONObject).get("question").toString()), null, null
                )
            )
        }
        return questions
    }*/
}
