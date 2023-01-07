package com.example.realmdatabase

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class DataModel  (
    @PrimaryKey
    var id:Int?=null,
    var name: String? = null,
    var email: String? = null
): RealmObject()
