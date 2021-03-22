package com.moin.sparknetworks.lib_persistencestorage

import io.reactivex.Completable

/**
 * Application Database contract to be implemented by the DB implementation.
 */
interface PersistenceStorageContract {

    /**
     * Init  the DB.
     */
    fun getDB(): Any

    /**
     * Init  the DB.
     */
    fun getRefreshDB(): Any

    /**
     * Close the DB.
     */
    fun close()

    /**
     * Clear the DB.
     * @return Com
     */
    fun clear(): Any
}
