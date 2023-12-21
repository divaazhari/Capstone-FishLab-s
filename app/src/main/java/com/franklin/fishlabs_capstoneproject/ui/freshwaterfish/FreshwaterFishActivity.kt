package com.franklin.fishlabs_capstoneproject.ui.freshwaterfish

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.franklin.fishlabs_capstoneproject.R
import com.franklin.fishlabs_capstoneproject.data.api.ApiService
import com.franklin.fishlabs_capstoneproject.model.MainModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FreshwaterFishActivity : AppCompatActivity() {
    private val TAG : String = "Freshwater List"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_freshwater_fish)
    }

    override fun onStart() {
        super.onStart()
        //getDataFromApi()
    }
    //private fun getDataFromApi() {

        ////ApiService.endpoint.getImage()
           // .enqueue(object : Callback<List<MainModel>> {
               // override fun onResponse(
                   // call: Call<List<MainModel>>,
                    //response: Response<List<MainModel>>,
              //  ) {
                   // TODO("Not yet implemented")
             //   }

               // override fun onFailure(call: Call<List<MainModel>>, t: Throwable) {
               //     TODO("Not yet implemented")
               // }

           // })


   // }
    private fun printLog(message : String) {
        Log.d(TAG, message)
    }
}