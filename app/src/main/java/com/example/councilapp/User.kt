package com.example.councilapp

data class User(var status:String?=null,var location:String?=null,var reportBy:String?=null,
                var assetType:String?=null,var reportDate:String?=null,var notes:String?=null)
//  should be same with Firestore  including name  and  type