package com.moin.sparknetworks.lib_persistencestorage.db

import io.reactivex.Completable

/**
 * Application Database contract to be implemented by the DB implementation.
 */
internal interface DBContract {
    /**
     * Set up the initial configuration.
     */
    fun configuration()

    /**
     * Init  the DB.
     */
    fun getDB(): Any

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
