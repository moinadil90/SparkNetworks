package com.moin.sparknetworks.presentation.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.moin.sparknetworks.R
import com.moin.sparknetworks.click
import com.moin.sparknetworks.gone
import com.moin.sparknetworks.model.storage.records.QuestionRecord
import com.moin.sparknetworks.show
import io.realm.Realm
import kotlinx.android.synthetic.main.layout_personality_adapter_item.view.*


class PersonalityAdapter(private val context: Context) :
    RecyclerView.Adapter<PersonalityAdapter.ViewHolder>() {

    private var questionsList = mutableListOf<QuestionRecord>()
    private var hardfactList = mutableListOf<QuestionRecord>()
    private var lifestyleList = mutableListOf<QuestionRecord>()
    private var introversionList = mutableListOf<QuestionRecord>()
    private var passionList = mutableListOf<QuestionRecord>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_personality_adapter_item,
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.question.text = questionsList[position].question.toString()
        val options = questionsList[position].question_type?.options
        val selected = questionsList[position].question_type?.selectedValue

        if (options?.size == 3) {
            viewHolder.itemView.option_4.gone()
        } else {
            viewHolder.itemView.option_4.show()
        }

        options?.forEachIndexed { index, s ->
            when (index) {
                0 -> {
                    if (s == selected) {
                        viewHolder.itemView.option_1.isChecked = true
                    }
                    viewHolder.itemView.option_1.text = options[index]
                }
                1 -> {
                    if (s == selected) {
                        viewHolder.itemView.option_2.isChecked = true
                    }
                    viewHolder.itemView.option_2.text = options[index]
                }
                2 -> {
                    viewHolder.itemView.option_3.text = options[index]
                    if (s == selected) {
                        viewHolder.itemView.option_3.isChecked = true
                    }
                }
                3 -> {
                    if (s == selected) {
                        viewHolder.itemView.option_4.isChecked = true
                    }
                    viewHolder.itemView.option_4.text = options[index]
                }

            }
        }

        viewHolder.itemView.option_1.click {

            Realm.getDefaultInstance().executeTransaction {
                questionsList[position].question_type?.selectedValue = options?.get(0).toString()
            }
            Realm.getDefaultInstance().close()
            showToast()
        }

        viewHolder.itemView.option_2.click {
            Realm.getDefaultInstance().executeTransaction {
                questionsList[position].question_type?.selectedValue = options?.get(1).toString()
            }
            Realm.getDefaultInstance().close()
            showToast()
        }
        viewHolder.itemView.option_3.click {
            Realm.getDefaultInstance().executeTransaction {
                questionsList[position].question_type?.selectedValue = options?.get(2).toString()
            }
            Realm.getDefaultInstance().close()
            showToast()

        }
        viewHolder.itemView.option_4.click {
            Realm.getDefaultInstance().executeTransaction {
                questionsList[position].question_type?.selectedValue = options?.get(3).toString()
            }
            Realm.getDefaultInstance().close()
            showToast()
        }

    }

    override fun getItemCount(): Int {
        return questionsList.size
    }

    fun setData(t: List<QuestionRecord>) {
        separateListByCategory(t)
        questionsList.addAll(hardfactList)
        questionsList.addAll(lifestyleList)
        questionsList.addAll(introversionList)
        questionsList.addAll(passionList)
    }

    private fun separateListByCategory(questions: List<QuestionRecord>) {
        for (i in 0..questions.size.minus(1)) {
            when {
                //hard_fact list
                questions[i].category.equals("hard_fact") -> questions[i].let {
                    hardfactList.add(
                        it
                    )
                }
                //lifestyle list
                questions[i].category.equals("lifestyle") -> questions[i].let {
                    lifestyleList.add(
                        it
                    )
                }
                //introversion list
                questions[i].category.equals("introversion") -> questions[i].let {
                    introversionList.add(
                        it
                    )
                }
                //passion list
                else -> questions[i].let { passionList.add(it) }
            }
        }
    }

    private fun showToast() {
        Toast.makeText(context, context.getString(R.string.toast_msg), Toast.LENGTH_SHORT)
            .show()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}