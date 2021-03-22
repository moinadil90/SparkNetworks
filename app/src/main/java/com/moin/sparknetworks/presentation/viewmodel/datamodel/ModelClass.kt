package com.moin.sparknetworks.presentation.viewmodel.datamodel

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class MyPojo : RealmObject() {
    var questions: RealmList<Questions>? = null
    var categories: RealmList<String>? = null
    @PrimaryKey
    var id : String? = ""

}


open class Questions : RealmObject() {
    var question: String? = null
    var questionType: QuestionType? = null
    var category: String? = null

}


open class QuestionType : RealmObject() {
    var condition: Condition? = null
    var options: RealmList<String>? = null
    var type: String? = null
    var selectedValue  = ""
    var range: Range? = null
}

open class Condition : RealmObject() {
    var predicate: Predicate? = null
    var if_positive: If_positive? = null
}


open class If_positive : RealmObject() {
    var question: String? = null
    var questionType: QuestionType? = null
    var category: String? = null

}


open class Range : RealmObject() {
    var from: String? = null
    var to: String? = null

}

open class Predicate : RealmObject() {
    var exactEquals: RealmList<String>? = null

}









