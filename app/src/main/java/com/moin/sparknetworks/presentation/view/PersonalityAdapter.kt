package com.moin.sparknetworks.presentation.view

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.moin.sparknetworks.R
import com.moin.sparknetworks.click
import com.moin.sparknetworks.gone
import com.moin.sparknetworks.model.storage.records.QuestionRecord
import com.moin.sparknetworks.show
import io.realm.Realm
import kotlinx.android.synthetic.main.layout_personality_adapter_item.view.*
import kotlinx.android.synthetic.main.layout_personality_header.view.*


class PersonalityAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1

    private var questionsList = mutableListOf<QuestionRecord>()
    private var hardfactList = mutableListOf<QuestionRecord>()
    private var lifestyleList = mutableListOf<QuestionRecord>()
    private var introversionList = mutableListOf<QuestionRecord>()
    private var passionList = mutableListOf<QuestionRecord>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == TYPE_HEADER) {
            return HeaderViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.layout_personality_header,
                    parent,
                    false
                )
            )
        } else {
            return ItemViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.layout_personality_adapter_item,
                    parent,
                    false
                )
            )
        }

    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is ItemViewHolder) {
            (context.getString(R.string.bullet) + questionsList[position].question.toString()).also {
                viewHolder.itemView.question.text = it
            }
            val options = questionsList[position].question_type?.options
            val selectedOptionValue = questionsList[position].question_type?.selectedValue

            if (options?.size == 3) {
                viewHolder.itemView.option_4.gone()
            } else {
                viewHolder.itemView.option_4.show()
            }

            setBackgroundBasedOnCategory(questionsList[position].category, viewHolder)

            options?.forEachIndexed { index, alreadySelectedValue ->
                when (index) {
                    0 -> {
                        if (alreadySelectedValue == selectedOptionValue) {
                            viewHolder.itemView.option_1.isChecked = true
                        }
                        viewHolder.itemView.option_1.text = options[index]
                    }
                    1 -> {
                        if (alreadySelectedValue == selectedOptionValue) {
                            viewHolder.itemView.option_2.isChecked = true
                        }
                        viewHolder.itemView.option_2.text = options[index]
                    }
                    2 -> {
                        viewHolder.itemView.option_3.text = options[index]
                        if (alreadySelectedValue == selectedOptionValue) {
                            viewHolder.itemView.option_3.isChecked = true
                        }
                    }
                    3 -> {
                        if (alreadySelectedValue == selectedOptionValue) {
                            viewHolder.itemView.option_4.isChecked = true
                        }
                        viewHolder.itemView.option_4.text = options[index]
                    }

                }
            }

            viewHolder.itemView.option_1.click {

                Realm.getDefaultInstance().executeTransaction {
                    questionsList[position].question_type?.selectedValue =
                        options?.get(0).toString()
                }
                Realm.getDefaultInstance().close()
                showToast()
            }

            viewHolder.itemView.option_2.click {
                Realm.getDefaultInstance().executeTransaction {
                    questionsList[position].question_type?.selectedValue =
                        options?.get(1).toString()
                }
                Realm.getDefaultInstance().close()
                showToast()
            }
            viewHolder.itemView.option_3.click {
                Realm.getDefaultInstance().executeTransaction {
                    questionsList[position].question_type?.selectedValue =
                        options?.get(2).toString()
                }
                Realm.getDefaultInstance().close()
                showToast()

            }
            viewHolder.itemView.option_4.click {
                Realm.getDefaultInstance().executeTransaction {
                    questionsList[position].question_type?.selectedValue =
                        options?.get(3).toString()
                }
                Realm.getDefaultInstance().close()
                showToast()
            }

        } else {
            //Type Header ViewHolder
            viewHolder.itemView.text_header.text = questionsList[position].category.toString()
            viewHolder.itemView.text_header.paintFlags =
                viewHolder.itemView.text_header.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            setBackgroundBasedOnCategory(questionsList[position].category.toString(), viewHolder)
        }
    }

    private fun setBackgroundBasedOnCategory(category: String?, viewHolder: RecyclerView.ViewHolder) {
        if (viewHolder is ItemViewHolder) {
            when (category) {
                PersonalityActivity.STRING_HARD_FACT -> {
                    viewHolder.itemView.setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.background_color
                        )
                    )
                }
                PersonalityActivity.STRING_LIFE_STYLE -> {
                    viewHolder.itemView.setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.colorPrimary
                        )
                    )
                }
                PersonalityActivity.STRING_INTROVERSION -> {
                    viewHolder.itemView.setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.background_color
                        )
                    )
                }
                else -> {
                    viewHolder.itemView.setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.colorPrimary
                        )
                    )
                }
            }
        } else {
             when(category) {
                 PersonalityActivity.HARD_FACT_LIST -> {
                     viewHolder.itemView.setBackgroundColor(
                         ContextCompat.getColor(
                             context,
                             R.color.background_color
                         )
                     )
                 }
                 PersonalityActivity.LIFE_STYLE_LIST -> {
                     viewHolder.itemView.setBackgroundColor(
                         ContextCompat.getColor(
                             context,
                             R.color.colorPrimary
                         )
                     )
                 }
                 PersonalityActivity.INTROVERSION_LIST -> {
                     viewHolder.itemView.setBackgroundColor(
                         ContextCompat.getColor(
                             context,
                             R.color.background_color
                         )
                     )
                 }
                 else -> {
                     viewHolder.itemView.setBackgroundColor(
                         ContextCompat.getColor(
                             context,
                             R.color.colorPrimary
                         )
                     )
                 }
             }
        }

    }

    override fun getItemCount(): Int {
        return (questionsList.size)
    }

    override fun getItemViewType(position: Int): Int {
        return if (isHeaderType(position)) {
            TYPE_HEADER
        } else {
            TYPE_ITEM
        }

    }

    fun setData(t: List<QuestionRecord>) {
        separateListByCategory(t)
        questionsList.addAll(hardfactList)
        questionsList.addAll(lifestyleList)
        questionsList.addAll(introversionList)
        questionsList.addAll(passionList)
    }

    private fun isHeaderType(position: Int): Boolean {
        if (questionsList[position].question == null) return true
        return false
    }

    private fun separateListByCategory(questions: List<QuestionRecord>) {
        addHeaderForEachListType()
        for (i in 0..questions.size.minus(1)) {
            when {
                //hard_fact list
                questions[i].category.equals(PersonalityActivity.STRING_HARD_FACT) -> questions[i].let {
                    hardfactList.add(
                        it
                    )
                }
                //lifestyle list
                questions[i].category.equals(PersonalityActivity.STRING_LIFE_STYLE) -> questions[i].let {
                    lifestyleList.add(
                        it
                    )
                }
                //introversion list
                questions[i].category.equals(PersonalityActivity.STRING_INTROVERSION) -> questions[i].let {
                    introversionList.add(
                        it
                    )
                }
                //passion list
                else -> questions[i].let { passionList.add(it) }
            }
        }
    }

    private fun addHeaderForEachListType() {
        hardfactList.add(QuestionRecord(category = PersonalityActivity.HARD_FACT_LIST))
        lifestyleList.add(QuestionRecord(category = PersonalityActivity.LIFE_STYLE_LIST))
        introversionList.add(QuestionRecord(category = PersonalityActivity.INTROVERSION_LIST))
        passionList.add(QuestionRecord(category = PersonalityActivity.PASSION_LIST))
    }

    private fun showToast() {
        Toast.makeText(context, context.getString(R.string.toast_msg), Toast.LENGTH_SHORT)
            .show()
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}