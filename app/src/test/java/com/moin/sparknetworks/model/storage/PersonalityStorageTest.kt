package com.moin.sparknetworks.model.storage

import android.content.Context;

import com.moin.sparknetworks.model.storage.dao.PersonalityDAOContract;
import com.moin.sparknetworks.model.storage.records.QuestionRecord;
import com.moin.sparknetworks.model.storage.records.QuestionTypeTest;
import com.moin.sparknetworks.utils.readJSONFromAsset

import org.assertj.core.api.AssertionsForClassTypes;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify


@RunWith(MockitoJUnitRunner::class)
class PersonalityStorageTest {

    lateinit var
            personalityStorage: PersonalityStorageContract

    lateinit var
            questionRecord: List<QuestionRecord>

    @Mock
    lateinit var
            mockPersonalityDAO: PersonalityDAOContract

    @Mock
    var context: Context? = null

    lateinit var
            personalityObject: JSONObject

    @Before

    fun setup() {
        MockitoAnnotations.initMocks(this)
        personalityStorage = PersonalityStorage(mockPersonalityDAO)
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
        `when`(mockPersonalityDAO.save(personalityObject)).thenReturn(Completable.complete())

        //Given
        personalityStorage.save(personalityObject).test().assertComplete()

        //Then
        verify(mockPersonalityDAO).save(personalityObject)
    }

    @Test
    fun check_save_failure() {
        //When
        `when`(mockPersonalityDAO.save(personalityObject)).thenReturn(
            Completable.error(
                Throwable()
            )
        )

        //Given
        personalityStorage.save(personalityObject).test().assertError(Throwable::class.java)

        //Then
        verify(mockPersonalityDAO).save(personalityObject)
    }

    @Test
    fun fetchQuestionFromDB_Success() {
        // Given
        `when`(mockPersonalityDAO.getQuestions()).thenReturn(
            Single.just(
                questionRecord
            )
        )

        // When
        personalityStorage.fetchQuestionsFromDB().test().values().run {
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
        verify(mockPersonalityDAO, Mockito.times(1)).getQuestions()
    }

    @Test
    fun fetchQuestionFromDB_Failure() {
        // Given
        `when`(mockPersonalityDAO.getQuestions()).thenReturn(Single.error(Throwable()))

        // When
        personalityStorage.fetchQuestionsFromDB().test().assertError(Throwable::class.java)

        //Verify
        verify(mockPersonalityDAO, Mockito.times(1)).getQuestions()
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

