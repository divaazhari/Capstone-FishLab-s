package com.franklin.fishlabs_capstoneproject.ui.fish

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.franklin.fishlabs_capstoneproject.R
import com.franklin.fishlabs_capstoneproject.data.api.ApiService
import com.franklin.fishlabs_capstoneproject.data.api.FileUploadResponse
import com.franklin.fishlabs_capstoneproject.databinding.ActivityCameraPickerBinding
import com.franklin.fishlabs_capstoneproject.getImageUri
import com.franklin.fishlabs_capstoneproject.ui.fish.CameraActivity.Companion.CAMERAX_RESULT
import com.franklin.fishlabs_capstoneproject.uriToFile
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException

class CameraPickerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraPickerBinding
    private var currentImageUri: Uri? = null
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission request dinied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        )== PackageManager.PERMISSION_GRANTED
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraPickerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        binding.cameraXButton.setOnClickListener {startCameraX() }
        binding.uploadButton.setOnClickListener {uploadImage() }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri)
    }
    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) {isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERAX_RESULT) {
            currentImageUri = it.data?.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)?.toUri()
            showImage()
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView.setImageURI(it)
        }
    }
    private fun uploadImage() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this)
            Log.d("Image File", "showImage: ${imageFile.path}")
            val description = "Ini adalah deksripsi gambar"
            showLoading(true)

            val requestBody = description.toRequestBody("text/plain".toMediaType())
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "photo",
                imageFile.name,
                requestImageFile
            )

            lifecycleScope.launch {
                try {
                    val apiService = ApiService.getApiService()
                    val successResponse = apiService.uploadImage(multipartBody, requestBody)
                    showToast(successResponse.message)
                    showLoading(false)
                } catch (e: HttpException) {
                    val errorBody = e.response()?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, FileUploadResponse::class.java)
                    showToast(errorResponse.message)
                    showLoading(false)
                }
            }
        } ?: showToast(getString(R.string.empty_image_warning))
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    companion object {
        private const val REQUIRED_PERMISSION = android.Manifest.permission.CAMERA
    }
}