package com.moin.sparknetworks.model.storage.records

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class CategoryRecord(var categories: RealmList<String> = listOf<String>() as RealmList<String>) : RealmObject()