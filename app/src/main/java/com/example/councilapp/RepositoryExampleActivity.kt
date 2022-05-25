package com.example.councilapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.councilapp.repository.*
import com.example.councilapp.repository.Admins.getAllAdmins
import com.example.councilapp.repository.Comments.deleteComment
import com.example.councilapp.repository.Comments.getReportAllComments
import com.example.councilapp.repository.Reports.getAllReports
import com.example.councilapp.repository.Residents.getAllResidents
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.grpc.Context
import java.util.*

const val TAG = "REPOSITORIES_EXAMPLES"

class RepositoryExampleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository_examples)
        val mockAdminUid1 = "mock_admin_uid_1"
        val mockResidentUid1 = "mock_resident_uid_1"

        /* To try things out, you should execute the below one at a time. */
        //addAdminExample(mockAdminUid1)
        //addResidentExample(mockResidentUid1)
        //addReportExample(mockResidentUid1)
        //changeReportAdminExample(mockAdminUid1)
        //changeReportStatusExample()
        //addCommentExample(mockResidentUid1)
        //addPhotoExample()

        //getAllAdminsExample()
        //getAdminExample(mockAdminUid1)
        //getAllResidentsExample()
        //getResidentExample(mockResidentUid1)
        //getAllReportsExample()
        //getReportExample()
        //getReportAllCommentsExample()
        //getCommentExample()
        //getReportAllPhotosExample()
        //getPhotoExample()

        //deleteAdminExample(mockAdminUid1)
        //deleteResidentExample(mockResidentUid1)
        //deleteReportExample()
        //deleteCommentExample()
        //deletePhotoExample()
    }

    private fun addAdminExample(exampleAdminUid: String) {
        /**
         * Note you don't have to call repository method inside another function. I'm doing this
         * because it's easier for me to organise the examples.
         */
/*==================================================================================================
 |  Example: Add an admin to Cloud Firestore
 *------------------------------------------------------------------------------------------------*/
        Admins.addAdmin(
            uid = exampleAdminUid, /* I'm passing in an argument for this, but you should use
            `Firebase.auth.currentUser.uid` to get the actual uid of the current user. */
            fullName = "Jone Doe",
            dateOfBirth = "10/10/1999", // Optional argument
            phoneNumber = "0412345678", // Optional argument
            address = "100 Queen St", // Optional argument
            city = "Melbourne", // Optional argument
            state = "VIC", // Optional argument
            postcode = "3000", // Optional argument
            wipFun = { /* This parameter is optional. It will be executed before the method talks
            to firebase. */
                Log.i(TAG, "addAdminExample: Adding new admin...") /* As an example, I logged a
                messaged here, but you can do whatever you want. */
            },
            failFun = {
            /* Also optional. This will be executed if the method fails to add a new
            admin in firebase. Your callback function can be executed against `it:Exception` */
                Log.i(TAG, "addAdminExample: Adding new admin failed.") /* Again, do whatever
                you want here. */
            },
            doneFun = { /* Also optional. This will be executed as the final step regardless of
            whether the new admin is added successfully or not. */
                Log.i(TAG, "addAdminExample: Method execution has finished.") /* Again, do
                whatever you want here. */
            },
        ) { /* Also optional. It will be executed if adding the new admin is successful. */
            /* Again, do whatever you want in here. I logged the string representation of the new
            *  admin here as an example.
            * */
            Admins.getAdmin(exampleAdminUid) {
                Log.i(TAG, "addAdminExample: Successfully added new admin: $it")
            }
        }
/*__________________________________________________________________________________________________
 *================================================================================================*/
    }

    private fun addResidentExample(exampleResidentUid: String) {
/*==================================================================================================
 |  Example: Add a resident to Cloud Firestore
 *------------------------------------------------------------------------------------------------*/
        Residents.addResident(
            uid = exampleResidentUid, /* I'm passing in an argument for this, but you should use
            `Firebase.auth.currentUser.uid` to get the actual uid of the current user. */
            fullName = "Hui Forst",
            //dateOfBirth = , // Optional argument and I chose to not supply anything.
            //phoneNumber = , // Optional argument and I chose to not supply anything.
            //address = , // Optional argument and I chose to not supply anything.
            //city = , // Optional argument and I chose to not supply anything.
            //state = , // Optional argument and I chose to not supply anything.
            //postcode = , // Optional argument and I chose to not supply anything.
            emailAddress = "hui.forst@fakeemail.fake", // Optional argument and I chose to supply.
            /* wipFun = {}, // As an example, notice I could but I'm not supplying this argument
            because it's optional. */
            /* failFun = {}, // Similar to the above, I'm not supplying this optional argument. */
            /* doneFun = {}, // Again, optional, and chose not to supply anything. */
        ) /* {} // Yet another optional argument which I chose not to supply. */
/*__________________________________________________________________________________________________
 *================================================================================================*/
    }

    private fun addReportExample(exampleResidentUid: String) {
/*==================================================================================================
 |  Example: Add a report to Cloud Firestore
 *------------------------------------------------------------------------------------------------*/
        Reports.addReport(
            location = GeoPoint(-37.82308545741673, 145.03877856167546), /* You
            should call the phone's location API to get the actual location of the reporting
            resident. */
            reportedByUid = exampleResidentUid, /* I'm passing in an argument for this. You should
            use a resident's actual UID. */
            reportStatus = "NEW", /* I'm passing in a string literal as an example. Ideally this
            should be a member of an Enum defined somewhere. */
            assetType = "Rubbish Bin",
            notes = "Notes",
            wipFun = { // Optional
                Log.i(TAG, "addReportExample: Adding new report...")
            },
            // failFun = {}, // Optional and I chose not to supply it.
            doneFun = { // Optional
                Log.i(TAG, "addReportExample: Method execution has finished.")
            },
        ) { reportRef -> /* Optional `successFun`. NOTE `reportRef` (the added report's document id
        in Firestore) is available as a parameter here in case you want to do something about the
        report as soon as it is added to Firestore. For example, you may want to allocate it to an
        admin immediately (because a new report does not get assigned a responsible admin when it's
        newly submitted by the reporting resident), or you may want to store photos of the report in
        Firebase, or the initial comment made by the reporting resident. */
            /* As an example, I logged the string representation of the newly added report below.*/
            Reports.getReport(reportRef) {
                Log.i(TAG, "addReportExample: Successfully added new report: $it")
            }
        }
/*__________________________________________________________________________________________________
 *================================================================================================*/
    }

    private fun changeReportAdminExample(exampleAdminUid: String) {
/*==================================================================================================
 |  Example: Change the responsible admin of a report.
 *------------------------------------------------------------------------------------------------*/
        Reports.changeReportAdmin(
            reportRef = "RPT00001",
            adminUid = exampleAdminUid, /* I'm passing in an argument for this. You should use an
            admin's actual UID. */
            //wipFun = {}, // Optional.
            //failFun = {}, // Optional.
            //doneFun = {}, // Optional.
        ) { // Optional `successFun`. Do whatever you want in here.
            /* As an example, I logged the string representation of the report just to observe the
            outcome of the admin change. */
            Reports.getReport("RPT00001") {
                Log.i(TAG, "changeReportAdminExample: Successfully changed admin of report RPT00001: $it")
            }
        }
/*__________________________________________________________________________________________________
 *================================================================================================*/
    }

    private fun changeReportStatusExample() {
/*==================================================================================================
 |  Example: Change the status of a report.
 *------------------------------------------------------------------------------------------------*/
        Reports.changeReportStatus(
            reportRef = "RPT00001",
            newStatus = "IN PROGRESS", /* I'm passing in a string literal here. Idealy this should be
            a member of a Enum defined somewhere. */
            //wipFun = {}, // Optional.
            //failFun = {}, // Optional.
            //doneFun = {}, // Optional.
        ) { // Optional `successFun`. Do whatever you want in here.
            /* As an example, I logged the string representation of the report just to observe the
            outcome of the status change. */
            Reports.getReport("RPT00001") {
                Log.i(TAG, "changeReportStatusExample: Successfully changed status of report RPT00001: $it")
            }
        }
/*__________________________________________________________________________________________________
 *================================================================================================*/
    }

    private fun addCommentExample(exampleCommenterUid: String) {
/*==================================================================================================
 |  Example: Add a comment to an existing report in Cloud Firestore.
 *------------------------------------------------------------------------------------------------*/
        Comments.addComment(
            reportRef = "RPT00001", /* The report to which you want to add the comment. */
            reportStatus = "NEW", /* The status of the report when comment is added. */
            commenterUid = exampleCommenterUid, /* The id of the resident/admin who authored the
            comment. I'm passing in an argument, but you should use the actual UID of the user
            submitting the comment. */
            commentText = "Some trash person doodled on the traffic light and it created a potential safety hazard.",
            //wipFun = {}, // Optional.
            //failFun = {}, // Optional.
            //doneFun = {}, // Optional.
        ) { commentId -> /* `successFun`: optional. NOTE the comment ID is available for your to use
        as the argument of your callback `successFun` in case you want to do something immediately
        with it. For example, you may want to immediately display the comment somewhere on the
        screen. */
            /* As an example, I logged the string representation of the newly added comment below.*/
            Comments.getComment("RPT00001", commentId) {
                Log.i(TAG, "addCommentExample: Successfully added comment to RPT00001: $it")
            }
        }
/*__________________________________________________________________________________________________
 *================================================================================================*/
    }

    private fun addPhotoExample() {
/*==================================================================================================
 |  Example: Add a photo to an existing report in Cloud Firestore.
 *------------------------------------------------------------------------------------------------*/
        Photos.addPhoto(
            reportRef = "RPT00001", /* The report to which you want to add the photo. */
            filePath = "$filesDir/example_asset_photo_1.png", /* The file must have been cached or
            saved on the device first. */
            //wipFun = {}, // Optional.
            //failFun = {}, // Optional.
            //doneFun = {}, // Optional.
        ) { photoId -> /* `successFun`: optional. NOTE the photo ID is available for your to use
        as the argument of your callback `successFun` in case you want to do something immediately
        with it. For example, you may want to immediately display the photo somewhere on the
        screen. */
            /* As an example, I logged the string representation of the newly added photo below.*/
            Photos.getPhoto("RPT00001", photoId) {
                Log.i(TAG, "addPhotoExample: Successfully added photo to RPT00001: $it")
            }
        }
/*__________________________________________________________________________________________________
 *================================================================================================*/
    }

    private fun getAllAdminsExample() {
/*==================================================================================================
 |  Example: Obtain the details of all admins from Firestore.
 *------------------------------------------------------------------------------------------------*/
        Admins.getAllAdmins(
            //wipFun = {}, // Optional.
            //failFun = {}, // Optional.
            //doneFun = {}, // Optional.
        ) { allAdmins -> /* `successFun`: NOT optional. Here's where you do what you want to do with
        a list of all the admins you've obtained from Firestore. */
            /* As an example, I'm logging the string representation of the obtained admins. */
            Log.i(TAG, "getAllAdminsExample: Obtained all admins $allAdmins")
        }
/*__________________________________________________________________________________________________
 *================================================================================================*/
    }

    private fun getAdminExample(exampleAdminUid: String) {
/*==================================================================================================
 |  Example: Obtain the details of an admin from Firestore.
 *------------------------------------------------------------------------------------------------*/
        Admins.getAdmin(
            uid = exampleAdminUid, /* You should use the actual uid of an admin. */
            //wipFun = {}, // Optional.
            //failFun = {}, // Optional.
            //doneFun = {}, // Optional.
        ) { admin -> /* `successFun`: NOT optional. Here's where you do what you want to do with the
        obtained admin. */
            /* As an example, I'm logging the string representation of the obtained admin. */
            Log.i(TAG, "getAdminExample: Obtained admin $admin")
        }
/*__________________________________________________________________________________________________
 *================================================================================================*/
    }

    private fun getAllResidentsExample() {
/*==================================================================================================
 |  Example: Obtain the details of all residents from Firestore.
 *------------------------------------------------------------------------------------------------*/
        Residents.getAllResidents(
            //wipFun = {}, // Optional.
            //failFun = {}, // Optional.
            //doneFun = {}, // Optional.
        ) { allResidents -> /* `successFun`: NOT optional. Here's where you do what you want to do with
        a list of all the residents you've obtained from Firestore. */
            /* As an example, I'm logging the string representation of the obtained residents. */
            Log.i(TAG, "getAllResidentsExample: Obtained all residents $allResidents")
        }
/*__________________________________________________________________________________________________
 *================================================================================================*/
    }

    private fun getResidentExample(exampleResidentUid: String) {
/*==================================================================================================
 |  Example: Obtain the details of a resident from Firestore.
 *------------------------------------------------------------------------------------------------*/
        Residents.getResident(
            uid = exampleResidentUid,
            //wipFun = {}, // Optional.
            //failFun = {}, // Optional.
            //doneFun = {}, // Optional.
        ) { resident -> /* `successFun`: NOT optional. Here's where you do what you want to do with
        the resident you've obtained. */
            /* As an example, I'm logging the string representation of the obtained resident. */
            Log.i(TAG, "getResidentExample: Obtained resident $resident")
        }
/*__________________________________________________________________________________________________
 *================================================================================================*/
    }

    private fun getAllReportsExample() {
/*==================================================================================================
 |  Example: Obtain the details of all reports from Firestore.
 *------------------------------------------------------------------------------------------------*/
        Reports.getAllReports(
            //wipFun = {}, // Optional.
            //failFun = {}, // Optional.
            //doneFun = {}, // Optional.
        ) { allReports -> /* `successFun`: NOT optional. Here's where you do what you want to do with
        all the reports you've obtained from Firestore. */
            /* As an example, I'm logging the string representation of the obtained reports. */
            Log.i(TAG, "getAllReportsExample: Obtained all reports $allReports")
        }
/*__________________________________________________________________________________________________
 *================================================================================================*/
    }

    private fun getReportExample() {
/*==================================================================================================
 |  Example: Obtain the details of a report from Firestore.
 *------------------------------------------------------------------------------------------------*/
        Reports.getReport(
            reportRef = "RPT00001"
            //wipFun = {}, // Optional.
            //failFun = {}, // Optional.
            //doneFun = {}, // Optional.
        ) { report -> /* `successFun`: NOT optional. Here's where you do what you want to do with
        the report you've obtained from Firestore. */
            /* As an example, I'm logging the string representation of the obtained report. */
            Log.i(TAG, "getReportExample: Obtained report $report")
        }
/*__________________________________________________________________________________________________
 *================================================================================================*/
    }

    private fun getReportAllCommentsExample() {
/*==================================================================================================
 |  Example: Obtain all comments of a report from Firestore.
 *------------------------------------------------------------------------------------------------*/
        Comments.getReportAllComments(
            reportRef = "RPT00001",
            //wipFun = {}, // Optional.
            //failFun = {}, // Optional.
            //doneFun = {}, // Optional.
        ) { allReportComments -> /* `successFun`: NOT optional. Here's where you do what you want to
        do with all the comments you've obtained from Firestore. */
            /* As an example, I'm logging the string representation of the obtained comments. */
            Log.i(TAG, "getReportAllCommentsExample: Obtained comments $allReportComments")
        }
/*__________________________________________________________________________________________________
 *================================================================================================*/
    }

    private fun getCommentExample() {
/*==================================================================================================
 |  Example: Obtain a comment from Firestore.
 *------------------------------------------------------------------------------------------------*/
        Comments.getComment(
            reportRef = "RPT00001",
            commentId ="HM5NO017byeKRt6VkiV6",
            //wipFun = {}, // Optional.
            //failFun = {}, // Optional.
            //doneFun = {}, // Optional.
        ) { comment -> /* `successFun`: NOT optional. Here's where you do what you want to
        do with the comment you've obtained from Firestore. */
            /* As an example, I'm logging the string representation of the obtained comment. */
            Log.i(TAG, "getCommentExample: Obtained comment $comment")
        }
/*__________________________________________________________________________________________________
 *================================================================================================*/
    }

    private fun getReportAllPhotosExample() {
/*==================================================================================================
 |  Example: Obtain all comments of a report from Firestore.
 *------------------------------------------------------------------------------------------------*/
        Photos.getReportAllPhotos(
            reportRef = "RPT00001",
            //wipFun = {}, // Optional.
            //failFun = {}, // Optional.
            //doneFun = {}, // Optional.
        ) { allReportPhotos -> /* `successFun`: NOT optional. Here's where you do what you want to
        do with all the photos you've obtained from Firestore. */
            /* As an example, I'm logging the string representation of the obtained photos. */
            Log.i(TAG, "getReportAllPhotosExample: Obtained photos $allReportPhotos")
        }
/*__________________________________________________________________________________________________
 *================================================================================================*/
    }

    private fun getPhotoExample() {
/*==================================================================================================
 |  Example: Obtain a photo from Firestore.
 *------------------------------------------------------------------------------------------------*/
        Photos.getPhoto(
            reportRef = "RPT00001",
            photoId ="jDVS2mOz1MEsgOAEXlpy",
            //wipFun = {}, // Optional.
            //failFun = {}, // Optional.
            //doneFun = {}, // Optional.
        ) { photo -> /* `successFun`: NOT optional. Here's where you do what you want to
        do with the photo you've obtained from Firestore. */
            /* As an example, I'm logging the string representation of the obtained photo. */
            Log.i(TAG, "getPhotoExample: Obtained photo $photo")
        }
/*__________________________________________________________________________________________________
 *================================================================================================*/
    }

    private fun deleteAdminExample(exampleAdminUid: String) {
/*==================================================================================================
 |  Example: Delete an admin in Firestore.
 *------------------------------------------------------------------------------------------------*/
        Admins.deleteAdmin(
            uid = exampleAdminUid,
            //wipFun = {}, // Optional.
            //failFun = {}, // Optional.
            //doneFun = {}, // Optional.
        ) { /* Optional `successFun`. */
            /* As an example, I'm logging a message to signal the deletion was successful. */
            Log.i(TAG, "deleteAdminExample: Admin deleted successfully.")
        }
/*__________________________________________________________________________________________________
 *================================================================================================*/
    }

    private fun deleteResidentExample(exampleResidentUid: String) {
/*==================================================================================================
 |  Example: Delete a resident in Firestore.
 *------------------------------------------------------------------------------------------------*/
        Residents.deleteResident(
            uid = exampleResidentUid,
            //wipFun = {}, // Optional.
            //failFun = {}, // Optional.
            //doneFun = {}, // Optional.
        ) { /* Optional `successFun`. */
            /* As an example, I'm logging a message to signal the deletion was successful. */
            Log.i(TAG, "deleteResidentExample: Resident deleted successfully.")
        }
/*__________________________________________________________________________________________________
 *================================================================================================*/
    }

    private fun deleteReportExample() {
/*==================================================================================================
 |  Example: Delete a report in Firestore.
 *------------------------------------------------------------------------------------------------*/
        Reports.deleteReport(
            reportRef = "RPT00002",
            //wipFun = {}, // Optional.
            //failFun = {}, // Optional.
            //doneFun = {}, // Optional.
        ) { /* Optional `successFun`. */
            /* As an example, I'm logging a message to signal the deletion was successful. */
            Log.i(TAG, "deleteReportExample: Report deleted successfully.")
        }
/*__________________________________________________________________________________________________
 *================================================================================================*/
    }

    private fun deleteCommentExample() {
/*==================================================================================================
 |  Example: Delete a comment in Firestore.
 *------------------------------------------------------------------------------------------------*/
        Comments.deleteComment(
            reportRef = "RPT00001",
            commentId = "HM5NO017byeKRt6VkiV6",
            //wipFun = {}, // Optional.
            //failFun = {}, // Optional.
            //doneFun = {}, // Optional.
        ) { /* Optional `successFun`. */
            /* As an example, I'm logging a message to signal the deletion was successful. */
            Log.i(TAG, "deleteCommentExample: Comment deleted successfully.")
        }
/*__________________________________________________________________________________________________
 *================================================================================================*/
    }

    private fun deletePhotoExample() {
/*==================================================================================================
 |  Example: Delete a photo in Firestore and in Firebase Storage.
 *------------------------------------------------------------------------------------------------*/
        Photos.deletePhoto(
            reportRef = "RPT00001",
            photoId = "yGwi36Y1OiPUhACqt78O",
            //wipFun = {}, // Optional.
            //failFun = {}, // Optional.
            //doneFun = {}, // Optional.
        ) { /* Optional `successFun`. */
            /* As an example, I'm logging a message to signal the deletion was successful. */
            Log.i(TAG, "deletePhotoExample: Photo deleted successfully.")
        }
/*__________________________________________________________________________________________________
 *================================================================================================*/
    }
}