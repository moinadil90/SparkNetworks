package com.moin.sparknetworks.lib_persistencestorage.db.realm

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.webkit.CookieManager
import com.moin.sparknetworks.lib_persistencestorage.PersistenceStorageChangeContract
import com.moin.sparknetworks.lib_persistencestorage.RealmModuleContract
import com.moin.sparknetworks.lib_persistencestorage.db.DBConfig.Companion.dbVersion
import com.moin.sparknetworks.lib_persistencestorage.db.DBConfig.Companion.realmDBName
import com.moin.sparknetworks.lib_persistencestorage.db.DBContract
import io.reactivex.Completable
import io.reactivex.CompletableSource
import io.reactivex.observers.DisposableCompletableObserver
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.exceptions.RealmException
import timber.log.Timber

/**
 * Sets up the default configuration and initializes Realm DB.
 */
internal class RealmDB(
    private val context: Context/*,
                       private val moduleList: List<RealmModuleContract>,
                       private val changeList: List<PersistenceStorageChangeContract>*/
) :
    DBContract {

    init {
        Realm.init(context)  // Initialize Realm
        println("Test from init()")
        configuration() // Set the configuration
    }

    override fun getDB(): Any {
        return Realm.getDefaultInstance()
    }

    override fun getRefreshDB(): Any =
        (getDB() as Realm).apply { if (!isInTransaction) refresh() } // Refresh only when it is outside any transaction.

    override fun configuration() {
        try {
            getRealmConfiguration()
        } catch (e: Exception) {
            Timber.e(e.toString())
        }
    }

    override fun close() {
        try {
            (getDB() as Realm).executeTransactionAsync {
                it.removeAllChangeListeners()
                it.close()
            }
        } catch (e: RealmException) {
            Timber.e(e)
        }
    }

    override fun clear(): Any {
        return Realm.getApplicationContext()?.deleteDatabase("sparknetworks.realm")!!
    }

    /*override fun clear(): Completable = Completable.create { emitter ->
        changeList.run {
            val completableSourceList = mutableListOf<CompletableSource>()
            forEach {
                completableSourceList.add(it.clearStorage(getRefreshDB() as Realm)) // Sequential call
            }
            Completable.mergeArray(*completableSourceList.toTypedArray())
                    .doFinally {
                        Handler(Looper.getMainLooper()).post {
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                                CookieManager.getInstance().removeAllCookies { Timber.d("Cookies Cleared!!") }
                            }
                        }
                    }.subscribe(object : DisposableCompletableObserver() {
                        override fun onComplete() {
                            emitter.onComplete()
                        }

                        override fun onError(e: Throwable) {
                            Timber.e(e)
                            emitter.onError(e)
                        }
                    })
        }
    }*/

    private fun getRealmConfiguration() {
        val realmConfigurationBuilder = RealmConfiguration.Builder()
            .allowWritesOnUiThread(true)
            .name(realmDBName)
            .schemaVersion(dbVersion)
            .build()
        Realm.setDefaultConfiguration(realmConfigurationBuilder)
    }
}
