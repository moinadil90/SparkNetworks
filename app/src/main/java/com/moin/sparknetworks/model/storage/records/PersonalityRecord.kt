package com.moin.sparknetworks.model.storage.records

open class PersonalityRecord(
    var questions: List<QuestionRecord>? = null,
    var categories: List<CategoryRecord>? = null
)