package com.moin.sparknetworks.lib_persistencestorage.db

class DBConfig {
    companion object {
        const val realmDBName = "sparknetworks.realm"
        const val dbVersion: Long = 6
        const val encryptedKeyName = "sparknetworks"
        const val encryptionFilePrefix = "encrypted_"
    }
}
