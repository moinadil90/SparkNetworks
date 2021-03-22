package com.moin.sparknetworks.lib_persistencestorage;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;
import timber.log.Timber;

/**
 * This class is the wrapper class over the RealM database. This class consist of type of crud
 * operations required to do the database operation.
 *
 * @param <T> Any class of kind {@link RealmObject}
 */
public class RealmWrapper<T extends RealmObject> {

  private PersistenceStorage storageDB;

  public RealmWrapper(PersistenceStorage storageDB) {
    this.storageDB = storageDB;
  }

  /**
   * Used to insert or update the single {@link RealmObject}.
   *
   * @param t This is the {@link RealmObject}
   * @return boolean indicates that the functionality is completed or not.
   */
  public boolean insertOrUpdateData(T t) {
    Realm realm = getRealM();
    boolean resultOk = false;
    try {
      realm.beginTransaction();
      realm.copyToRealmOrUpdate(t);
      realm.commitTransaction();
      resultOk = true;
    } catch (Exception e) {
      cancelRealmTransaction(realm);
      Timber.e(e);
    }
    return resultOk;
  }

  /**
   * Used to insert or update the {@link List} of {@link RealmObject}.
   *
   * @param dataList This is {@link List} of {@link RealmObject}.
   * @return boolean indicates that the functionality is completed or not.
   */
  public boolean insertOrUpdateData(List<T> dataList) {
    Realm realm = getRealM();
    boolean resultOk = false;
    try {
      realm.beginTransaction();
      realm.copyToRealmOrUpdate(dataList);
      realm.commitTransaction();
      resultOk = true;
    } catch (Exception e) {
      cancelRealmTransaction(realm);
      Timber.e(e);
    }
    return resultOk;
  }

  /**
   * This is used to get all records in {@link RealmObject} without any condition.
   *
   * @param clazz This is reference of {@link RealmObject}.
   * @return All available records.
   */
  public List<T> query(Class<T> clazz) {
    Realm realm = getRealM();
    List<T> list = new ArrayList<>();
    try {
      realm.beginTransaction();
      RealmResults<T> resultSet = realm.where(clazz).findAll();
      list = realm.copyFromRealm(resultSet);
      realm.commitTransaction();
      return list;
    } catch (Exception e) {
      cancelRealmTransaction(realm);
      Timber.e(e);
    }
    return list;
  }

  /**
   * This is used to get first record in {@link RealmObject} with applied conditions.
   *
   * @param clazz This is reference of {@link RealmObject}.
   * @param fieldName This is the field to apply criteria.
   * @param value This is the value of the field.
   * @return RealmObject after applying criteria.
   */
  public T query(Class<T> clazz, String fieldName, String value) {
    Realm realm = getRealM();
    try {
      return realm.where(clazz).equalTo(fieldName, value).findFirst();
    } catch (Exception e) {
      cancelRealmTransaction(realm);
      Timber.e(e);
    }
    return null;
  }

  /**
   * This is used to get first record in {@link RealmObject} with applied conditions.
   *
   * @param clazz This is reference of {@link RealmObject}.
   * @param fieldName This is the field to apply criteria.
   * @param value This is the value of the field.
   * @return RealmObject after applying criteria.
   */
  public T query(Class<T> clazz, String fieldName, int value) {
    Realm realm = getRealM();
    try {
      return realm.where(clazz).equalTo(fieldName, value).findFirst();
    } catch (Exception e) {
      cancelRealmTransaction(realm);
      Timber.e(e);
    }
    return null;
  }

  /**
   * This is used to get first record.
   *
   * @param clazz This is reference of {@link RealmObject}.
   * @return RealmObject record.
   */
  public T queryFirst(Class<T> clazz) {
    Realm realm = getRealM();
    try {
      return realm.where(clazz).findFirst();
    } catch (Exception e) {
      cancelRealmTransaction(realm);
      Timber.e(e);
    }
    return null;
  }

  /** This is used to refresh the realmDB. */
  public Realm refreshRealm() {
    Realm realm = getRealM();
    realm.refresh();
    return realm;
  }

  /**
   * This is used to get all records in {@link RealmObject} with applied conditions.
   *
   * @param clazz This is reference of {@link RealmObject}.
   * @param fieldName This is the field to apply criteria.
   * @param value This is the value of the field.
   * @return List of RealmObject after applying criteria.
   */
  public List<T> queryAll(Class<T> clazz, String fieldName, String value) {
    Realm realm = getRealM();
    List<T> targetList = new ArrayList<>();
    try {
      RealmResults<T> resultSet = realm.where(clazz).equalTo(fieldName, value).findAll();
      targetList.addAll(realm.copyFromRealm(resultSet));
      return targetList;
    } catch (Exception e) {
      cancelRealmTransaction(realm);
      Timber.e(e);
    }
    return targetList;
  }

  /**
   * This is used to get all records in {@link RealmResults} without any condition.
   *
   * @param clazz This is reference of {@link RealmObject}.
   * @return All available records.
   */
  public RealmResults<T> queryForRealmResult(Class<T> clazz) {
    Realm realm = getRealM();
    RealmResults<T> realmResult = null;
    try {
      realm.beginTransaction();
      realmResult = realm.where(clazz).findAll();
      realm.commitTransaction();
    } catch (Exception e) {
      cancelRealmTransaction(realm);
      Timber.e(e);
    }
    return realmResult;
  }

  /**
   * This is used to get all records in {@link RealmObject} with applied conditions.
   *
   * @param clazz This is reference of {@link RealmObject}.
   * @param fieldName This is the field to apply criteria.
   * @param sortOrder True for ASCENDING and false for DESCENDING
   * @return List of RealmObject after applying criteria.
   */
  public List<T> queryAll(Class<T> clazz, String fieldName, Sort sortOrder) {
    Realm realm = getRealM();
    List<T> targetList = new ArrayList<>();
    try {
      RealmResults<T> resultSet = realm.where(clazz).findAll().sort(fieldName, sortOrder);
      targetList.addAll(realm.copyFromRealm(resultSet));
      return targetList;
    } catch (Exception e) {
      Timber.e(e);
      cancelRealmTransaction(realm);
    }
    return targetList;
  }

  /**
   * This is used to get all records in {@link RealmObject} with applied conditions.
   *
   * @param clazz This is reference of {@link RealmObject}
   * @param fieldName This is the field to apply criteria
   * @param value This is the value of the field
   * @param sortFieldName name of column to sort the list.
   * @param sortOrder True for ASCENDING and false for DESCENDING
   * @return List of RealmObject after applying criteria
   */
  public List<T> queryAll(
      Class<T> clazz, String fieldName, String value, String sortFieldName, Sort sortOrder) {
    Realm realm = getRealM();
    List<T> targetList = new ArrayList<>();
    try {
      RealmResults<T> resultSet =
          realm.where(clazz).equalTo(fieldName, value).findAll().sort(sortFieldName, sortOrder);
      targetList.addAll(realm.copyFromRealm(resultSet));
      return targetList;
    } catch (Exception e) {
      Timber.e(e);
      cancelRealmTransaction(realm);
    }
    return targetList;
  }

  /**
   * This is used to get all records in {@link RealmObject} with multiple applied conditions.
   *
   * @param clazz This is reference of {@link RealmObject}
   * @param filter map for filter conditions
   * @param sortFieldName name of column to sort the list.
   * @param sortOrder True for ASCENDING and false for DESCENDING
   * @return List of RealmObject after applying criteria
   */
  public List<T> queryAll(
      Class<T> clazz, Map<String, String[]> filter, String sortFieldName, Sort sortOrder) {
    Realm realm = getRealM();
    List<T> targetList = new ArrayList<>();
    try {
      RealmQuery<T> realmQuery = realm.where(clazz);
      for (Entry<String, String[]> entry : filter.entrySet()) {
        realmQuery.in(entry.getKey(), entry.getValue());
      }
      RealmResults<T> resultSet = realmQuery.findAll().sort(sortFieldName, sortOrder);
      targetList.addAll(realm.copyFromRealm(resultSet));
      return targetList;
    } catch (Exception e) {
      Timber.e(e);
      cancelRealmTransaction(realm);
    }
    return targetList;
  }

  /**
   * This is used to delete all record with applied conditions.
   *
   * @param clazz This is reference of {@link RealmObject}.
   * @param fieldName This is the field to apply criteria.
   * @param fieldValue This is the value of the field in string.
   * @return boolean indicates that the functionality is completed or not.
   */
  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  public boolean delete(Class<T> clazz, String fieldName, String fieldValue) {
    Realm realm = getRealM();
    boolean resultOk = false;
    try {
      realm.beginTransaction();
      Objects.requireNonNull(realm.where(clazz).equalTo(fieldName, fieldValue))
          .findAll()
          .deleteAllFromRealm();
      realm.commitTransaction();
      resultOk = true;
    } catch (Exception e) {
      cancelRealmTransaction(realm);
      Timber.e(e);
    }
    return resultOk;
  }

  /**
   * This is used to delete all record with applied conditions.
   *
   * @param clazz This is reference of {@link RealmObject}.
   * @param fieldName This is the field to apply criteria.
   * @param value This is the value of the field in double.
   * @return boolean indicates that the functionality is completed or not.
   */
  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  public boolean delete(Class<T> clazz, String fieldName, Double value) {
    Realm realm = getRealM();
    boolean resultOk = false;
    try {
      realm.beginTransaction();
      Objects.requireNonNull(realm.where(clazz).equalTo(fieldName, value))
          .findAll()
          .deleteAllFromRealm();
      realm.commitTransaction();
      resultOk = true;
    } catch (Exception e) {
      cancelRealmTransaction(realm);
      Timber.e(e);
    }
    return resultOk;
  }

  /**
   * This is used to delete all records.
   *
   * @param clazz This is reference of {@link RealmObject}.
   * @return boolean indicates that the functionality is completed or not.
   */
  public boolean deleteAll(Class<T> clazz) {
    Realm realm = getRealM();
    boolean resultOk = false;
    try {
      realm.beginTransaction();
      Objects.requireNonNull(realm.where(clazz)).findAll().deleteAllFromRealm();
      realm.commitTransaction();
      resultOk = true;
    } catch (Exception e) {
      cancelRealmTransaction(realm);
      Timber.e(e);
    }
    return resultOk;
  }

  /**
   * This is used to delete first record with applied conditions.
   *
   * @param clazz This is reference of {@link RealmObject}.
   * @param fieldName This is the field to apply criteria.
   * @param value This is the value of the field.
   * @return boolean indicates that the functionality is completed or not.
   */
  public boolean deleteFirst(Class<T> clazz, String fieldName, String value) {
    Realm realm = getRealM();
    boolean resultOk = false;
    try {
      realm.beginTransaction();
      Objects.requireNonNull(realm.where(clazz).equalTo(fieldName, value).findFirst())
          .deleteFromRealm();
      realm.commitTransaction();
      resultOk = true;
    } catch (Exception e) {
      cancelRealmTransaction(realm);
      Timber.e(e);
    }
    return resultOk;
  }

  /**
   * This is used to delete first record with applied conditions.
   *
   * @param clazz This is reference of {@link RealmObject}.
   * @param fieldName This is the field to apply criteria.
   * @param fieldValue This is the value of the field.
   * @return boolean indicates that the functionality is completed or not.
   */
  public boolean deleteFirst(Class<T> clazz, String fieldName, int fieldValue) {
    Realm realm = getRealM();
    boolean resultOk = false;
    try {
      realm.beginTransaction();
      Objects.requireNonNull(realm.where(clazz).equalTo(fieldName, fieldValue).findFirst())
          .deleteFromRealm();
      realm.commitTransaction();
      resultOk = true;
    } catch (Exception e) {
      cancelRealmTransaction(realm);
      Timber.e(e);
    }
    return resultOk;
  }

  private void cancelRealmTransaction(Realm realm) {
    if (realm.isInTransaction()) {
      realm.cancelTransaction();
    }
  }

  private Realm getRealM() {
    return (Realm) storageDB.getDB();
  }
}
