package com.example.councilapp

import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint


data class User(
    var status:String?=null,
    var location:GeoPoint?=null,
    var reportBy:String?=null,
    var assetType:String?=null,
    var reportDate: Timestamp? =null,
    var notes:String?=null)
//  should be same with Firestore  including name  and  type