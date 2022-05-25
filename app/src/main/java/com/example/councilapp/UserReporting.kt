package com.example.councilapp


import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.councilapp.databinding.ActivityUserReportingBinding
import com.example.councilapp.repository.Reports
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.GeoPoint


class UserReporting : AppCompatActivity(), OnMapReadyCallback {

    lateinit var ImageUri: Uri
    private val LOCATION_PERMISSION_REQUEST = 1
    private lateinit var map: GoogleMap
    lateinit var binding: ActivityUserReportingBinding
    private lateinit var fusedLocationProvidedClient: FusedLocationProviderClient
    private lateinit var tvLat: TextView
    private lateinit var tvLong: TextView


    private fun getLocationAccess() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true
        } else
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST
            )
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                map.isMyLocationEnabled = true
            } else {
                Toast.makeText(
                    this,
                    "User has not granted location access permission",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        getLocationAccess()

        // Add a marker in Sydney and move the camera
        //val sydney = LatLng(-34.0, 151.0)
        //mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            getLocationAccess()
            return
        }
        var fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationProviderClient.lastLocation.addOnCompleteListener(this) { task ->
            val location: Location? = task.result
            if (location == null) {
                Toast.makeText(this, "Null Received", Toast.LENGTH_SHORT).show()
            } else {
                tvLat.text = "" + location.latitude
                tvLong.text = "" + location.longitude
            }
        }
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK) {

            ImageUri = data?.data!!


        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        getCurrentLocation()

        binding = ActivityUserReportingBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val assetType = findViewById<EditText>(R.id.assetType)
        val notes = findViewById<EditText>(R.id.notes)
        val btnImage = findViewById<Button>(R.id.btn_image)
        val btnSubmit = findViewById<Button>(R.id.btn_submit)
        val btnCancel = findViewById<Button>(R.id.btn_cancel)
        val trackBtn = findViewById<Button>(R.id.trackBtn)

        tvLat = findViewById<TextView>(R.id.reportLat)
        tvLong = findViewById<TextView>(R.id.reportLong)



        binding.btnImage.setOnClickListener {

            selectImage()

        }

        btnSubmit.setOnClickListener {
            when {

                TextUtils.isEmpty(notes.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@UserReporting,
                        "Please enter your notes here",
                        Toast.LENGTH_LONG
                    ).show()
                }
                TextUtils.isEmpty(assetType.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@UserReporting,
                        "Please enter your asset type here",
                        Toast.LENGTH_LONG
                    ).show()
                }

                else -> {

                    Reports.addReport(
                        location = GeoPoint(
                            tvLat.text.toString().trim().toDouble(),
                            tvLong.text.toString().trim().toDouble()
                        ),
                        reportedByUid = intent.getStringExtra("user_id").toString(),
                        reportStatus = "NEW",
                        assetType = assetType.text.toString().trim(),
                        notes = notes.text.toString().trim(),
                        wipFun = { Log.i(TAG, "addReportExample: Adding new report...") },
                        doneFun = { // Optional
                            Log.i(TAG, "addReportExample: Method execution has finished.")
                        },
                    ) { reportRef ->
                        Reports.getReport(reportRef) {
                            Log.i(TAG, "addReportExample: Successfully added new report: $it")

                        }
                    }


                    Toast.makeText(
                        this@UserReporting,
                        "Report Has Been Successfully Added",
                        Toast.LENGTH_LONG
                    ).show()

                    startActivity(Intent(this@UserReporting, UserTracking::class.java))
                }
            }
        }

        btnCancel.setOnClickListener {
            startActivity(Intent(this@UserReporting, UserReporting::class.java))
        }

        trackBtn.setOnClickListener {
            startActivity(Intent(this@UserReporting, UserTracking::class.java))
        }

        binding.btnLog.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            startActivity(Intent(this@UserReporting, LoginActivity::class.java))
            finish()
        }
    }
}





