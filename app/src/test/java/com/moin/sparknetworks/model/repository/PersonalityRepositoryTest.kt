package com.moin.sparknetworks.model.repository

import android.content.Context
import com.moin.sparknetworks.model.storage.PersonalityStorageContract
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
class PersonalityRepositoryTest {

    lateinit var personalityRepository: PersonalityRepositoryContract

    lateinit var questionRecord: List<QuestionRecord>

    @Mock
    lateinit var mockPersonalityStorage: PersonalityStorageContract

    @Mock
    var context: Context? = null
    lateinit var personalityObject: JSONObject

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        personalityRepository = PersonalityRepository(mockPersonalityStorage)
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
        `when`(mockPersonalityStorage.save(personalityObject)).thenReturn(Completable.complete())

        //Given
        personalityRepository.save(personalityObject).test().assertComplete()

        //Then
        verify(mockPersonalityStorage).save(personalityObject)
    }

    @Test
    fun check_save_failure() {
        //When
        `when`(mockPersonalityStorage.save(personalityObject)).thenReturn(
            Completable.error(
                Throwable()
            )
        )

        //Given
        personalityRepository.save(personalityObject).test().assertError(Throwable::class.java)

        //Then
        verify(mockPersonalityStorage).save(personalityObject)
    }

    @Test
    fun fetchQuestionFromDB_Success() {
        // Given
        `when`(mockPersonalityStorage.fetchQuestionsFromDB()).thenReturn(
            Single.just(
                questionRecord
            )
        )

        // When
        personalityRepository.fetchQuestionsFromDB().test().values().run {
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
        verify(mockPersonalityStorage, Mockito.times(1)).fetchQuestionsFromDB()
    }

    @Test
    fun fetchQuestionFromDB_Failure() {
        // Given
        `when`(mockPersonalityStorage.fetchQuestionsFromDB()).thenReturn(Single.error(Throwable()))

        // When
        personalityRepository.fetchQuestionsFromDB().test().assertError(Throwable::class.java)

        //Verify
        verify(mockPersonalityStorage, Mockito.times(1)).fetchQuestionsFromDB()
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

