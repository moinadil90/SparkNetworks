package com.moin.sparknetworks.presentation.viewmodel

import android.content.Context
import android.database.DatabaseUtils
import com.moin.sparknetworks.domain.PersonalityInteractorContract
import com.moin.sparknetworks.model.storage.records.QuestionRecord
import com.moin.sparknetworks.model.storage.records.QuestionTypeTest
import com.moin.sparknetworks.utils.readJSONFromAsset
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.realm.Realm
import io.realm.Realm.getApplicationContext
import org.assertj.core.api.AssertionsForClassTypes
import org.json.JSONObject
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PersonalityViewModelTest {

    lateinit var personalityViewModel: PersonalityViewModelContract

    lateinit var questionRecord: List<QuestionRecord>

    @Mock
    lateinit var mockPersonalityInteractor: PersonalityInteractorContract

    @Mock
    lateinit var context: Context
    lateinit var personalityObject: JSONObject

    @Mock
    lateinit var mockRealm: Realm

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Realm.init(getApplicationContext());
        var db: DatabaseUtils
        personalityViewModel = PersonalityViewModel(mockPersonalityInteractor)
        loadTestData()
    }

    private fun loadTestData() {
        personalityObject = JSONObject(readJSONFromAsset(context?.applicationContext) ?: "")
        questionRecord = mockQuestionRecord()
    }

    @After
    fun afterRxScheduler() {
        RxAndroidPlugins.reset()
        RxJavaPlugins.reset()
    }

    @Test
    fun check_save_success() {
        //When
        `when`(mockPersonalityInteractor.save(personalityObject)).thenReturn(Completable.complete())

        //Given
        personalityViewModel.save(personalityObject).test().assertComplete()

        //Then
        verify(mockPersonalityInteractor).save(personalityObject)
    }

    @Test
    fun check_save_failure() {
        //When
        `when`(mockPersonalityInteractor.save(personalityObject)).thenReturn(
            Completable.error(
                Throwable()
            )
        )

        //Given
        personalityViewModel.save(personalityObject).test().assertError(Throwable::class.java)

        //Then
        verify(mockPersonalityInteractor).save(personalityObject)
    }

    @Test
    fun fetchQuestionFromDB_Success() {
        // Given
        `when`(mockPersonalityInteractor.fetchQuestionsFromDB()).thenReturn(
            Single.just(
                questionRecord
            )
        )

        // When
        personalityViewModel.fetchQuestionsFromDB().test().values().run {
            val questionRecordList = this[0]
            AssertionsForClassTypes.assertThat(questionRecordList.size == questionRecord.size)
                .isTrue()
            AssertionsForClassTypes.assertThat(questionRecordList[0].question == questionRecord[0].question)
                .isTrue()
            AssertionsForClassTypes.assertThat(questionRecordList[0].category == questionRecord[0].category)
                .isTrue()
            AssertionsForClassTypes.assertThat(questionRecordList[0].question_type == questionRecord[0].question_type)
                .isTrue()
            AssertionsForClassTypes.assertThat(questionRecordList[0].id == questionRecord[0].id)
                .isTrue()
        }

        //Verify
        verify(mockPersonalityInteractor, Mockito.times(1)).fetchQuestionsFromDB()
    }

    @Test
    fun fetchQuestionFromDB_Failure() {
        // Given
        `when`(mockPersonalityInteractor.fetchQuestionsFromDB()).thenReturn(Single.error(Throwable()))

        // When
        personalityViewModel.fetchQuestionsFromDB().test().assertError(Throwable::class.java)

        //Verify
        verify(mockPersonalityInteractor, Mockito.times(1)).fetchQuestionsFromDB()
    }


    private fun mockQuestionRecord(): List<QuestionRecord> {
        val questionRecordList = mutableListOf<QuestionRecord>()
        questionRecordList.add(
            QuestionRecord(
                "What is your gender?",
                QuestionTypeTest(),
                "hard_fact"
            )
        )
        questionRecordList.add(
            QuestionRecord(
                "How often do you smoke?",
                QuestionTypeTest(),
                "lifestyle"
            )
        )
        questionRecordList.add(
            QuestionRecord(
                "Do you enjoy going on holiday by yourself?",
                QuestionTypeTest(),
                "introversion"
            )
        )
        return questionRecordList
    }

}

