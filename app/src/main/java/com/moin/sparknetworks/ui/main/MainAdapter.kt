package com.moin.sparknetworks.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.moin.sparknetworks.R
import com.moin.sparknetworks.click
import com.moin.sparknetworks.gone
import com.moin.sparknetworks.show
import io.realm.RealmList
import kotlinx.android.synthetic.main.item_layout.view.*


class MainAdapter(private val context: Context) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private var myPojo: MyPojo? = null
    private var questionsList = mutableListOf<Questions>()
    private var hardfactList = mutableListOf<Questions>()
    private var lifestyleList = mutableListOf<Questions>()
    private var introversionList = mutableListOf<Questions>()
    private var passionList = mutableListOf<Questions>()
    private var viewModel: MainViewModel? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout,
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
            viewModel?.realm?.executeTransaction {
                questionsList[position].question_type?.selectedValue = options?.get(0).toString()
            }
            showToast()
        }
        viewHolder.itemView.option_2.click {
            viewModel?.realm?.executeTransaction {
                questionsList[position].question_type?.selectedValue = options?.get(1).toString()
            }
            showToast()
        }
        viewHolder.itemView.option_3.click {
            viewModel?.realm?.executeTransaction {
                questionsList[position].question_type?.selectedValue = options?.get(2).toString()
            }
            showToast()

        }
        viewHolder.itemView.option_4.click {
            viewModel?.realm?.executeTransaction {
                questionsList[position].question_type?.selectedValue = options?.get(3).toString()
            }
            showToast()
        }

    }

    override fun getItemCount(): Int {
        return questionsList.size
    }

    fun setData(
        myPojo: MyPojo?,
        viewModel: MainViewModel
    ) {
        this.myPojo = myPojo
        separateListByCategory(myPojo?.questions)
        questionsList.addAll(hardfactList)
        questionsList.addAll(lifestyleList)
        questionsList.addAll(introversionList)
        questionsList.addAll(passionList)
        this.viewModel = viewModel
    }

    private fun separateListByCategory(questions: RealmList<Questions>?) {
        for (i in 0..(questions?.size?.minus(1) ?: 0)) {
            when {
                questions?.get(i)?.category.equals("hard_fact") -> questions?.get(i)?.let {
                    hardfactList.add(
                        it
                    )
                }
                questions?.get(i)?.category.equals("lifestyle") -> questions?.get(i)?.let {
                    lifestyleList.add(
                        it
                    )
                }
                questions?.get(i)?.category.equals("introversion") -> questions?.get(i)?.let {
                    introversionList.add(
                        it
                    )
                }
                else -> questions?.get(i)?.let { passionList.add(it) }
            }
        }
    }

    private fun showToast() {
        Toast.makeText(context, context.getString(R.string.toast_msg), Toast.LENGTH_SHORT)
            .show()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}