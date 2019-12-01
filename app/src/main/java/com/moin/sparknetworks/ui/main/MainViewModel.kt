package com.moin.sparknetworks.ui.main

import androidx.lifecycle.ViewModel
import io.realm.Realm

class MainViewModel : ViewModel() {
     val realm = Realm.getDefaultInstance()
   fun getEntity() : MyPojo?{
       return realm.where(MyPojo::class.java).findFirst()
   }
}
