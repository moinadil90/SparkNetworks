package com.moin.sparknetworks.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.moin.sparknetworks.R
import com.moin.sparknetworks.di.DaggerQuestionComponent
import com.moin.sparknetworks.di.QuestionModule
import com.moin.sparknetworks.presentation.viewmodel.PersonalityViewModel
import javax.inject.Inject

class PersonalityActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: PersonalityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerQuestionComponent.builder()
            .questionModule(QuestionModule(applicationContext, this))
            .build().inject(this)

        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, PersonalityFragment.newInstance())
                .commitNow()
        }
    }

}
