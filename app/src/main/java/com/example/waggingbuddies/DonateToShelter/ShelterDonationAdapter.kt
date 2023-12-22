package com.example.waggingbuddies.DonateToShelter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.waggingbuddies.Activities.PaymentActivity
import com.example.waggingbuddies.DataClass.ShelterDataClass
import com.example.waggingbuddies.R
import java.lang.IndexOutOfBoundsException

class ShelterDonationAdapter(val context: Context, val activity: Activity): RecyclerView.Adapter<ShelterDonationAdapter.MyViewHolder>() {

    private val GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user"
    private val GOOGLE_PAY_REQUEST_CODE = 123
    private val UPI_ID = "916227406346@paytm"

    private var ShelterList = mutableListOf<ShelterDataClass>()


    @SuppressLint("NotifyDataSetChanged")
    fun setShelterList(shelterList: MutableLiveData<List<ShelterDataClass>?>){
        try {
            this.ShelterList = shelterList.value!!.toMutableList()
            Log.d("tag","shelterList --> ${this.ShelterList}")
            notifyDataSetChanged()
            Log.d("tag","notified the data set changed")
        }
        catch (e: Exception){
            Log.i("errorInRetrieval",e.toString())
        }

    }



    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var shelterName: TextView =itemView.findViewById(R.id.textShelterName)
        var shelterLocation: TextView =itemView.findViewById(R.id.textShelterLocation)
        var dogCapacity: TextView =itemView.findViewById(R.id.dogCapacity)
        var dogStrength: TextView =itemView.findViewById(R.id.dogStrength)
        var catCapacity: TextView =itemView.findViewById(R.id.catsCapacity)
        var catStrenth: TextView =itemView.findViewById(R.id.catsStrength)
        var cowCapacity: TextView =itemView.findViewById(R.id.cowsCapacity)
        var cowStrenth: TextView =itemView.findViewById(R.id.cowsStrength)
        var birdCapacity: TextView =itemView.findViewById(R.id.birdsCapacity)
        var birdStrenth: TextView =itemView.findViewById(R.id.birdsStrength)
        var dogLayout: LinearLayout =itemView.findViewById(R.id.DogDetails)
        var catLayout: LinearLayout =itemView.findViewById(R.id.CatDetails)
        var cowLayout: LinearLayout =itemView.findViewById(R.id.CowDetails)
        var birdLayout: LinearLayout =itemView.findViewById(R.id.BirdDetails)
        var btnDonate : Button = itemView.findViewById(R.id.btnDonate)
        var totalDonation : TextView = itemView.findViewById(R.id.textTotalDonations)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shelter_item_layout, parent, false)
        return MyViewHolder(view)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val obj = ShelterList[position]
        obj.let {
                shelter ->
            holder.shelterName.text = shelter.name
            holder.shelterLocation.text = shelter.address
            Log.i("Tag",shelter.address.toString())

            val stages : List<Int> = shelter.totalCapacity
            val str : List<Int> = shelter.currentStrength

            if (stages.size==0 || stages[0] == 0)
                holder.dogLayout.visibility = View.GONE
            if (stages.size<=1 ||stages[1] == 0)
                holder.catLayout.visibility = View.GONE
            if (stages.size<=2 ||stages[2] == 0)
                holder.cowLayout.visibility = View.GONE
            if (stages.size<=3 ||stages[3] == 0)
                holder.birdLayout.visibility = View.GONE

            try {
                holder.totalDonation.text = "Donation: ${shelter.donationReceived}"
                holder.dogCapacity.text= "Capacity: ${stages[0]}"
                holder.dogStrength.text="Strength: ${str[0]}"
                holder.catCapacity.text= "Capacity: ${stages[1]}"
                holder.catStrenth.text="Strength: ${str[1]}"
                holder.cowCapacity.text= "Capacity: ${stages[2]}"
                holder.cowStrenth.text="Strength: ${str[2]}"
                holder.birdCapacity.text= "Capacity: ${stages[3]}"
                holder.birdStrenth.text="Strength: ${str[3]}"
            } catch (e: IndexOutOfBoundsException){

            }


            holder.btnDonate.setOnClickListener {
                val builder : AlertDialog.Builder = AlertDialog.Builder(context)
                val dialogView: View = LayoutInflater.from(
                    context).inflate(R.layout.dialog_donate, null)
                builder.setView(dialogView)
                builder.setTitle("Donate to ${shelter.name}")
//                builder.setMessage(getString(R.string.tnc_shelter))
                builder.setPositiveButton("CONFIRM", DialogInterface.OnClickListener{ dialog, which->
                    val etAmount: EditText = dialogView.findViewById(R.id.etAmount)

                    val intent = Intent(context,PaymentActivity::class.java)
                    intent.putExtra("name", shelter.name)
                    intent.putExtra("amount", etAmount.text.toString())
                    intent.putExtra("shelterID", shelter.shelterID)
                    activity.startActivity(intent)

//                    val uri = getUpiPaymentUri(shelter.name, UPI_ID, "Donate this shelter", etAmount.text.toString())
//                    payWithGPay(uri, shelter, etAmount.text.toString())

                })
                builder.setNegativeButton("Cancel", DialogInterface.OnClickListener{ dialog, which->

                })
                val alertDialog: AlertDialog = builder.create()
                alertDialog.show()
            }



        }
    }

    override fun getItemCount(): Int {
            return ShelterList.size
    }



//    private fun isAppInstalled(context: Context, packageName: String): Boolean {
//        return try {
//            context.packageManager.getApplicationInfo(packageName, 0)
//            true
//        } catch (e: PackageManager.NameNotFoundException) {
//            true
//        }
//    }
//
//    private fun getUpiPaymentUri(name: String, upiId: String, transactionNote: String, amount: String): Uri {
//        return Uri.Builder()
//            .scheme("upi")
//            .authority("pay")
//            .appendQueryParameter("pa", upiId)
//            .appendQueryParameter("pn", name)
//            .appendQueryParameter("tn", transactionNote)
//            .appendQueryParameter("am", amount)
//            .appendQueryParameter("cu", "INR")
//            .build()
//    }
//
//    private fun payWithGPay(uri: Uri, shelter: ShelterDataClass, amount: String) {
//        if (isAppInstalled(context, GOOGLE_PAY_PACKAGE_NAME)) {
//            val intent = Intent(Intent.ACTION_VIEW)
//            intent.data = uri
//            intent.putExtra("shelterID", shelter.shelterID)
//            intent.putExtra("amount", amount)
//            intent.`package` = GOOGLE_PAY_PACKAGE_NAME
//            startActivityForResult(Activity(), intent, GOOGLE_PAY_REQUEST_CODE, null)
//        } else {
//            Toast.makeText(context, "Please Install GPay", Toast.LENGTH_SHORT).show()
//        }
//    }





}

