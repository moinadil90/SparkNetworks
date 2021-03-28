package com.moin.sparknetworks.presentation.viewmodel

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.moin.sparknetworks.domain.PersonalityInteractorContract
import com.moin.sparknetworks.model.storage.records.QuestionRecord
import com.moin.sparknetworks.model.storage.records.QuestionType
import com.moin.sparknetworks.utils.readJSONFromAsset
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
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
class PersonalityViewModelTest: AppCompatActivity() {

    lateinit var personalityViewModel: PersonalityViewModelContract

    lateinit var questionRecord: List<QuestionRecord>

    @Mock
    lateinit var mockPersonalityInteractor: PersonalityInteractorContract

    @Mock
    var mockContext: Context? = null

    lateinit var personalityObject: JSONObject

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        personalityViewModel = PersonalityViewModel(mockPersonalityInteractor)
        mockContext?.let { loadTestData(it) }
    }

    private fun loadTestData(mockContext: Context) {
        personalityObject = JSONObject(readJSONFromAsset(mockContext) ?: "")
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
                QuestionType(),
                "hard_fact"
            )
        )
        questionRecordList.add(
            QuestionRecord(
                "How often do you smoke?",
                QuestionType(),
                "lifestyle"
            )
        )
        questionRecordList.add(
            QuestionRecord(
                "Do you enjoy going on holiday by yourself?",
                QuestionType(),
                "introversion"
            )
        )
        return questionRecordList
    }

}
