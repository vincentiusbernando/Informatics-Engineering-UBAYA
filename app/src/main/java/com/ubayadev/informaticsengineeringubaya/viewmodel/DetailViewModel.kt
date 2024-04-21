package com.ubayadev.informaticsengineeringubaya.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ubayadev.informaticsengineeringubaya.model.Program
import com.ubayadev.informaticsengineeringubaya.view.MainActivity

class DetailViewModel(application: Application): AndroidViewModel(application) {
    val programLiveData = MutableLiveData<Program>()
    val programLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    var listPage=MutableLiveData<List<String>>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null
    fun refresh(id:String) {
        loadingLD.value = true
        programLoadErrorLD.value = false
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/anmp/detail.php"
        val stringRequest = object: StringRequest(
            Request.Method.POST, url,
            {
                Log.d("showvoley", it)
                val sType = object : TypeToken<List<Program>>() { }.type
                val result = Gson().fromJson<List<Program>>(it, sType)
                var temp=result as ArrayList<Program>
                programLiveData.value = temp[0]
                loadingLD.value = false
                splitTextIntoPages(programLiveData.value!!.deskripsi)
                Log.d("showvoley", result.toString())
            },
            {
                Log.d("showvoley", it.toString())
                programLoadErrorLD.value = false
                loadingLD.value = false
            })
        {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["id"] = id
                return params
            }
        }


        stringRequest.tag = TAG
        queue?.add(stringRequest)

        programLoadErrorLD.value = false
        loadingLD.value = false
    }
    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
    fun splitTextIntoPages(desc: String) {
        listPage.value= desc.split(". ")
    }

}