package com.moin.sparknetworks.lib_persistencestorage

/**
 * Application Database contract to be implemented by the DB implementation.
 */
interface PersistenceStorageContract {

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
