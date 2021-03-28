package com.moin.sparknetworks.lib_persistencestorage

import android.content.Context
import com.moin.sparknetworks.lib_persistencestorage.db.DBContract
import com.moin.sparknetworks.lib_persistencestorage.db.realm.RealmDB
import com.moin.sparknetworks.lib_persistencestorage.exception.DBNotInitializeException

object PersistenceStorage : PersistenceStorageContract {
    private var appDB: DBContract? = null

    fun init(context: Context) {
        appDB = RealmDB(context)
    }

    override fun getDB(): Any {
        return appDB?.getDB() ?: throw DBNotInitializeException()
    }

    override fun close() = appDB?.close() ?: throw DBNotInitializeException()

    override fun clear() = appDB?.clear() ?: throw DBNotInitializeException()
}
