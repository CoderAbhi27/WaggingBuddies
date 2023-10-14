package com.example.waggingbuddies.RegisterYrPet

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.cloudinary.Cloudinary
import com.example.waggingbuddies.DataClass.PetsDataClass
import com.example.waggingbuddies.DataClass.ShelterDataClass
import com.example.waggingbuddies.R
import com.example.waggingbuddies.RegisterYrPet.retrofit.RetrofitInstancePostPet
import com.example.waggingbuddies.ShelterRegistration.retrofit.ApiResponse
import com.example.waggingbuddies.ShelterRegistration.retrofit.RetrofitInstancePostShelter
import com.example.waggingbuddies.databinding.FragmentAdoptAPetBinding
import com.example.waggingbuddies.databinding.FragmentRegisterYourPetBinding
import com.example.waggingbuddies.databinding.FragmentShelterRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response


class RegisterYourPetFragment : Fragment(R.layout.fragment_register_your_pet) {
    private lateinit var binding: FragmentRegisterYourPetBinding
    private lateinit var datamodel : PetsDataClass
    private val GALLERY_REQUEST_CODE = 123
    var userEmail : String? = null
    var imageUrl : String? = null
    var gender : Boolean = false
    private val cloudinary = Cloudinary("cloudinary://762115353195243:0VQAtjYo2InnO-OoNi4Dkhzcam8@dw5mpcsjv")
    private val takePicturePreview = registerForActivityResult(ActivityResultContracts.TakePicturePreview())
    { bitmap ->
        if (bitmap != null)
        {
          binding.image.setImageBitmap(bitmap)

        }
    }
    private val requestPermission =registerForActivityResult(ActivityResultContracts.RequestPermission())
    {
            granted ->
        if(granted)
        {
            takePicturePreview.launch(null)
        }else{
            Toast.makeText(requireContext(),"Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    private  val onresult = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    { result ->
        Log.i("TAG","This is the result: ${result.data} ${result.resultCode}")
        onResultRecieved(GALLERY_REQUEST_CODE,result)
    }
    private fun onResultRecieved(requestCode: Int, result: ActivityResult?)
    {
        when(requestCode){
            GALLERY_REQUEST_CODE->{
                if (result?.resultCode== Activity.RESULT_OK){
                    result.data?.data?.let{
                            uri ->
                        Log.i("TAG","onResultRecieved: $uri")
                       uploadImageToCloudinary(uri)
                    }
                }else{
                    Log.e("TAG","onActivityResult: error in selecting image")
                }
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterYourPetBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        binding.image.setOnClickListener {
            if(ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED){
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                intent.type = "image/*"
                val mimeTypes = arrayOf("image/jpeg","image/jpeg","image/jpeg")
                intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes)
                intent.flags =Intent.FLAG_GRANT_READ_URI_PERMISSION
                onresult.launch(intent)
            }else{
                requestPermission.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }

        }
        binding.button5.setOnClickListener {
            if (binding.name.text.isEmpty() && binding.autoCompleteTxt.text.isEmpty() && binding.age.text.isEmpty() && binding.breed.text.isEmpty() &&
                binding.ownerName.text.isEmpty() && binding.petHealth.text.isEmpty() && binding.adoptionDays.text.isEmpty() && binding.petAdress.text.isEmpty()
                && binding.message.text.isEmpty() && !(binding.radioButtonMale.isChecked) && !(binding.radioButtonFemale.isChecked)
            ) {
                if (binding.name.text.isEmpty())
                    Toast.makeText(requireContext(), "Enter Your Buddies Name", Toast.LENGTH_SHORT)
                        .show()
                if (binding.autoCompleteTxt.text.isEmpty())
                    Toast.makeText(requireContext(), "Selet The type of Pet", Toast.LENGTH_SHORT)
                        .show()
                if (binding.age.text.isEmpty())
                    Toast.makeText(requireContext(), "Enter Your Buddies Age", Toast.LENGTH_SHORT)
                        .show()
                if (binding.breed.text.isEmpty())
                    Toast.makeText(requireContext(), "Enter Your Pets Breed", Toast.LENGTH_SHORT)
                        .show()
                if (binding.ownerName.text.isEmpty())
                    Toast.makeText(requireContext(), "Enter Your Name", Toast.LENGTH_SHORT).show()
                if (binding.petHealth.text.isEmpty())
                    Toast.makeText(requireContext(), "Give a health diagnosis", Toast.LENGTH_SHORT)
                        .show()
                if (binding.adoptionDays.text.isEmpty())
                    Toast.makeText(
                        requireContext(),
                        "Enter the number of days you can keep yr pet for",
                        Toast.LENGTH_SHORT
                    ).show()
                if (binding.petAdress.text.isEmpty())
                    Toast.makeText(
                        requireContext(),
                        "Enter Your Buddies Address",
                        Toast.LENGTH_SHORT
                    ).show()
                if (binding.message.text.isEmpty())
                    Toast.makeText(
                        requireContext(),
                        "Enter description of your Buddy",
                        Toast.LENGTH_SHORT
                    ).show()
                if (!(binding.radioButtonMale.isChecked) && !(binding.radioButtonFemale.isChecked))
                    Toast.makeText(
                        requireContext(),
                        "Enter Gender of your Buddy",
                        Toast.LENGTH_SHORT
                    ).show()
                if (binding.radioButtonFemale.isChecked) gender == true
                if (binding.radioButtonMale.isChecked) gender == false
            }
            else
            {
                val user = FirebaseAuth.getInstance().currentUser!!
                if (user != null) {
                    userEmail = user.email
                } else {
                    userEmail = "abhinavkr327@gmail.com"
                }


                if (imageUrl == null) {
                    imageUrl = "https://jooinn.com/images/dog-67.jpg"
                }
                val ageText = binding.age.text.toString()
                val petHealthText = binding.petHealth.text.toString()
                val adoptionDaysText = binding.adoptionDays.text.toString()
                val petAdressText = binding.petAdress.text.toString()

// Check if the strings are not empty before converting to numeric types
                val age = if (ageText.isNotEmpty()) ageText.toInt() else 0 // You can replace 0 with a default value
                val petHealth = if (petHealthText.isNotEmpty()) petHealthText.toInt() else 0
                val adoptionDays = if (adoptionDaysText.isNotEmpty()) adoptionDaysText.toLong() else 0
                val petAddress = if (petAdressText.isNotEmpty()) petAdressText else ""


                val datamodel = PetsDataClass(
                    binding.autoCompleteTxt.text.toString(),
                    binding.name.text.toString(),
                    age,
                    binding.breed.text.toString(),
                    binding.ownerName.text.toString(),
                    userEmail!!,
                    petHealth,
                    adoptionDays,
                    petAddress,
                    imageUrl!!,
                    binding.message.text.toString(),
                    gender
                )
                Log.i("dataModel when built",datamodel.toString())
                pushData(datamodel)


            }
        }







     }
    fun getFilePathFromUri(context: Context, uri: Uri): String? {
        var filePath: String = ""
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, projection, null, null, null)
        cursor?.use {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            it.moveToFirst()
            filePath = it.getString(columnIndex)
        }
        return filePath
    }


    fun uploadImageToCloudinary(selectedImageUri: Uri) {
        GlobalScope.launch(Dispatchers.IO) {

            val filePath = getFilePathFromUri(requireContext(), selectedImageUri)

            try {
                Log.i("TAG","onfilePathRecieved: $filePath")
                val result = cloudinary.uploader().upload(filePath, null)
                val publicId = result.getValue("public_id")
                Log.i("TAG","onPublicIdRecieved: $publicId")
                val imageUrl = cloudinary.url().generate(publicId.toString())
                Log.i("TAG","onimageUrlRecieved: $imageUrl")

                // Handle the image URL as needed (e.g., update UI)
                launch(Dispatchers.Main) {
                    // Update the UI with the imageUrl here
                    // For example, set the imageUrl to an ImageView or display it in a TextView.
                    Log.i("TAG", "onimageUrlRecieved: $imageUrl")
                    datamodel = datamodel.copy(petImageURL = imageUrl)

                    Picasso.get().load(imageUrl).into(binding.image)
                }
            } catch (e: Exception) {
                // Handle the upload error
                Log.e("error", e.toString())
                e.printStackTrace()
            }
        }
    }
    fun pushData(data: PetsDataClass) {


        Log.i("dataModel",data.toString())
        val call = RetrofitInstancePostPet().petPostApiService.uplaodData(data)
        call.enqueue(object : retrofit2.Callback<ApiResponse>{
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {



                Log.i("Tag", response.toString())

               Log.i("response", response.body()?.msg.toString())
                if (response.body() == null) {
                    Toast.makeText(
                        requireContext(),
                        "Something went wrong!" + response.code().toString(),
                        Toast.LENGTH_SHORT
                    ).show()

                }
                else
                    Toast.makeText(
                        requireContext(),
                        "Pet is Successfully Registered!!",
                        Toast.LENGTH_SHORT
                    ).show()


            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.i("Tag",t.toString())

                Toast.makeText(requireContext(),"Try again !!, It may happen first time",Toast.LENGTH_SHORT).show()
            }
        })
    }
}