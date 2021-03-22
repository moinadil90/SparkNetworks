package com.moin.sparknetworks.lib_persistencestorage.db.realm

import com.moin.sparknetworks.lib_persistencestorage.PersistenceStorageChangeContract
import io.realm.DynamicRealm
import io.realm.RealmMigration
import timber.log.Timber


/**
 * This class defines Realm database migration schema from older version to new version
 */
class RealmDBMigrations(private val changeList: List<PersistenceStorageChangeContract>?) : RealmMigration {

    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        Timber.d("StorageMigration: Migration started!!")
        changeList?.forEach {
            it.onStorageUpgrade(realm, oldVersion, newVersion)
        }
        Timber.d("StorageMigration: Yupiee...Migration completed successfully")
    }

    override fun hashCode(): Int {
        return this.javaClass.toString().length
    }

    override fun equals(other: Any?): Boolean {
        return other is RealmDBMigrations
    }
}
