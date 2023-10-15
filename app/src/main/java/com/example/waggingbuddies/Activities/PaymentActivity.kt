package com.example.waggingbuddies.Activities

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.*
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.waggingbuddies.AdoptPet.retrofit.PetApiService
import com.example.waggingbuddies.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//import com.tournaments.googlepaytest.databinding.ActivityMainBinding


class PaymentActivity : AppCompatActivity() {



    private val GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user"
    private val GOOGLE_PAY_REQUEST_CODE = 123
    private val UPI_ID = "7014648589@paytm"
    private var status: String? = null
    private var uri: Uri? = null

    private lateinit var shelterName : String
    private lateinit var shelterID : String
    private lateinit var amount : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        // Get the driver details and hiring fee from the intent

        shelterName = intent.getStringExtra("name")!!
        shelterID = intent.getStringExtra("shelterID")!!
        amount = intent.getStringExtra("amount")!!

        uri = getUpiPaymentUri(shelterName, UPI_ID, "adopt your pet", amount)
        payWithGPay()


    }

    private fun isAppInstalled(context: Context, packageName: String): Boolean {
        return try {
            context.packageManager.getApplicationInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            true
        }
    }

    private fun getUpiPaymentUri(name: String, upiId: String, transactionNote: String, amount: String): Uri {
        return Uri.Builder()
            .scheme("upi")
            .authority("pay")
            .appendQueryParameter("pa", upiId)
            .appendQueryParameter("pn", name)
            .appendQueryParameter("tn", transactionNote)
            .appendQueryParameter("am", amount)
            .appendQueryParameter("cu", "INR")
            .build()
    }

    private fun payWithGPay() {
        if (isAppInstalled(this, GOOGLE_PAY_PACKAGE_NAME)) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = uri
            intent.`package` = GOOGLE_PAY_PACKAGE_NAME
            startActivityForResult(intent, GOOGLE_PAY_REQUEST_CODE)
        } else {
            Toast.makeText(this@PaymentActivity, "Please Install GPay", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val status = data?.getStringExtra("Status")?.toLowerCase()
        if ((AppCompatActivity.RESULT_OK == resultCode) && status == "success") {
            Toast.makeText(this@PaymentActivity, "Transaction Successful", Toast.LENGTH_SHORT).show()
            onPaymentSuccessful(shelterID, amount)
        } else {
            Toast.makeText(this@PaymentActivity, "Transaction Failed", Toast.LENGTH_SHORT).show()
        }
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun onPaymentSuccessful(driverCode: String, amount: String){
        Toast.makeText(this,"Thank you for donating rs$amount to $shelterID", Toast.LENGTH_SHORT).show()

        updateDatabase(shelterID, amount)
    }

    private val petApiService: PetApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://masterstack-o2dh.onrender.com/") // Replace with your API base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        petApiService = retrofit.create(PetApiService::class.java)
    }


    private fun updateDatabase(shelterID: String?, donationAmount: String?) {
        shelterID?.let {
            // Create a RequestBody for the donation amount

            val amountRequestBody =
                donationAmount?.let { it1 -> RequestBody.create(MultipartBody.FORM, it1) }

            // Launch a coroutine on the IO dispatcher to make the network request
            MainScope().launch(Dispatchers.IO) {
                try {
                    // Call your API service to donate to a specific pet using a Multipart request
                    val response =
                        amountRequestBody?.let { it1 -> petApiService.donateToPet(shelterID, it1) }

                    if (response != null) {
                        if (response.isSuccessful) {
                            runOnUiThread {
                                Toast.makeText(this@PaymentActivity, "Donation Updated", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            val errorBody = response.errorBody()?.string()
                            runOnUiThread {
                                Toast.makeText(this@PaymentActivity, "Failed to update donation", Toast.LENGTH_SHORT).show()
                                // Handle the error further as needed
                                Log.e("UpdateDonation", "Error: $errorBody")
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    runOnUiThread {
                        Toast.makeText(this@PaymentActivity, "Network error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }



}

