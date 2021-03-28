package com.moin.sparknetworks.presentation.view

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.moin.sparknetworks.R
import com.moin.sparknetworks.di.DaggerQuestionComponent
import com.moin.sparknetworks.di.QuestionModule
import com.moin.sparknetworks.presentation.viewmodel.PersonalityViewModel
import javax.inject.Inject

class PersonalityActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: PersonalityViewModel

    companion object {
        const val HARD_FACT_LIST = "Hard Fact List"
        const val LIFE_STYLE_LIST = "Lifestyle List"
        const val INTROVERSION_LIST = "Introversion List"
        const val PASSION_LIST = "Passion List"

        const val STRING_HARD_FACT = "hard_fact"
        const val STRING_LIFE_STYLE = "lifestyle"
        const val STRING_INTROVERSION = "introversion"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerQuestionComponent.builder()
            .questionModule(QuestionModule(applicationContext, this))
            .build().inject(this)

        setContentView(R.layout.layout_personality_activity)

        supportActionBar?.setBackgroundDrawable(ColorDrawable((ContextCompat.getColor(this, R.color.orange))))

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.parent_container, PersonalityFragment.newInstance())
                .commitNow()
        }
    }

}
