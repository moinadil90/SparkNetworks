package com.moin.sparknetworks.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.moin.sparknetworks.R
import com.moin.sparknetworks.model.storage.records.QuestionRecord
import com.moin.sparknetworks.utils.readJSONFromAsset
import io.reactivex.Single
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableSingleObserver
import io.realm.Realm
import kotlinx.android.synthetic.main.layout_personality_fragment.*
import org.json.JSONObject
import timber.log.Timber


class PersonalityFragment : Fragment() {

    companion object {
        fun newInstance() = PersonalityFragment()
    }

    private var personalityAdapter: PersonalityAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.layout_personality_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        personalityAdapter = context?.let { PersonalityAdapter(it) }

        //Only once, get JSON object from "personality_test.json" asset and save it to RealmDB, when user launches the App for the first time.
        if (Realm.getDefaultInstance().where(QuestionRecord::class.java).findFirst() == null) {
            val personalityObject =
                JSONObject(readJSONFromAsset(activity?.applicationContext) ?: "")
            saveQuestionsToDB(personalityObject)
        }

        fetchQuestionsFromDB()

        item_rv.layoutManager = LinearLayoutManager(activity)
        item_rv.itemAnimator = DefaultItemAnimator()

        item_rv.adapter = personalityAdapter
    }

    /**
     * Subscribe to it to save personalityObject to RealmDB.
     *
     * @param personalityObject object which contains questions and categories,
     * @return A {@Link Completable} to indicate whether this request is completed or not
     */
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

    /**
     * Subscribe to it in order to get the List of [QuestionRecord].
     *
     * @return A [Single] emitting the list of [QuestionRecord].
     */
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
}
