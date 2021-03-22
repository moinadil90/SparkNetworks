package com.moin.sparknetworks.lib_persistencestorage.db.realm

import io.realm.FieldAttribute
import io.realm.RealmObjectSchema

fun RealmObjectSchema.addIfNotExists(columnName: String, fieldType: Class<*>, vararg attributes: FieldAttribute) {
    if (!hasField(columnName)) this.addField(columnName, fieldType, *attributes)
}

fun RealmObjectSchema.removeIfExists(columnName: String) {
    if (hasField(columnName)) this.removeField(columnName)
}
