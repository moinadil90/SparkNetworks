package com.moin.sparknetworks.domain

import android.content.Context
import com.moin.sparknetworks.model.repository.PersonalityRepositoryContract
import com.moin.sparknetworks.model.storage.records.QuestionRecord
import com.moin.sparknetworks.model.storage.records.QuestionTypeTest
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
class PersonalityInteractorTest {

    lateinit var personalityInteractor: PersonalityInteractorContract

    lateinit var questionRecord: List<QuestionRecord>

    @Mock
    lateinit var mockPersonalityRepository: PersonalityRepositoryContract

    @Mock
    var context: Context? = null

    lateinit var personalityObject: JSONObject

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        personalityInteractor = PersonalityInteractor(mockPersonalityRepository)
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
        `when`(mockPersonalityRepository.save(personalityObject)).thenReturn(Completable.complete())

        //Given
        personalityInteractor.save(personalityObject).test().assertComplete()

        //Then
        verify(mockPersonalityRepository).save(personalityObject)
    }

    @Test
    fun check_save_failure() {
        //When
        `when`(mockPersonalityRepository.save(personalityObject)).thenReturn(
            Completable.error(
                Throwable()
            )
        )

        //Given
        personalityInteractor.save(personalityObject).test().assertError(Throwable::class.java)

        //Then
        verify(mockPersonalityRepository).save(personalityObject)
    }

    @Test
    fun fetchQuestionFromDB_Success() {
        // Given
        `when`(mockPersonalityRepository.fetchQuestionsFromDB()).thenReturn(
            Single.just(
                questionRecord
            )
        )

        // When
        personalityInteractor.fetchQuestionsFromDB().test().values().run {
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
        verify(mockPersonalityRepository, Mockito.times(1)).fetchQuestionsFromDB()
    }

    @Test
    fun fetchQuestionFromDB_Failure() {
        // Given
        `when`(mockPersonalityRepository.fetchQuestionsFromDB()).thenReturn(Single.error(Throwable()))

        // When
        personalityInteractor.fetchQuestionsFromDB().test().assertError(Throwable::class.java)

        //Verify
        verify(mockPersonalityRepository, Mockito.times(1)).fetchQuestionsFromDB()
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

