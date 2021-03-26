package com.moin.sparknetworks

import android.content.Context
import com.moin.sparknetworks.lib_persistencestorage.PersistenceStorage

object PersistenceStorageManager {
    private lateinit var context: Context

    /**
     * Init the storage.
     */
    fun init(context: Context) {
        this.context = context
        PersistenceStorage.init(context)
    }

    /**
     * Close the storage.
     */
    fun close() = PersistenceStorage.close()

}
