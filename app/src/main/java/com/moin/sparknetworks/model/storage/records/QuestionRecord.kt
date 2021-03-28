package com.moin.sparknetworks.model.storage.records

import com.moin.sparknetworks.utils.rand
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class QuestionRecord(
    var question: String? = null,
    var question_type: QuestionType? = null,
    var category: String? = null,
    @PrimaryKey
    var id: Int = rand(0, 25)
) : RealmObject()

open class QuestionType : RealmObject() {
    var options: RealmList<String>? = null
    var type: String? = null
    var selectedValue = ""
}