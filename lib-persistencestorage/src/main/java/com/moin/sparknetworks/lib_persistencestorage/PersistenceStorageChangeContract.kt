package com.moin.sparknetworks.lib_persistencestorage

import io.reactivex.Completable
import io.realm.DynamicRealm
import io.realm.Realm

/**
 * Migration contract needs to be implemented by the dependent Modules.
 */
interface PersistenceStorageChangeContract {
    /**
     * Clear the DB data.
     *
     * @return Completable of the action.
     */
    fun clearStorage(realm: Realm): Completable

    /**
     * Method to be executed on DB upgrade to handle the schema migration.
     *
     * @param realm: instance of the realm db.
     * @param oldVersion: older or current version of the db.
     * @param newVersion: upgraded db version.
     * @return Completable of the migration.
     */
    fun onStorageUpgrade(realm: DynamicRealm, oldVersion: Long, newVersion: Long)
}
