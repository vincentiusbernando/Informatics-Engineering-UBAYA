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
import com.ubayadev.informaticsengineeringubaya.model.Acc
import com.ubayadev.informaticsengineeringubaya.model.Program
import com.ubayadev.informaticsengineeringubaya.view.MainActivity

class HomeViewModel(application: Application): AndroidViewModel(application) {
    val programLiveData = MutableLiveData<ArrayList<Program>>()
    val usernameLiveData=MutableLiveData<String>()
    val programLoadErrorLD = MutableLiveData<Boolean>()
    val usernameLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null
    private var queue2: RequestQueue? = null
    fun refresh() {
        //program
        loadingLD.value = true
        programLoadErrorLD.value = false
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/anmp/select_programs.php"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<List<Program>>() { }.type
                val result = Gson().fromJson<List<Program>>(it, sType)
                programLiveData.value = result as ArrayList<Program>?
                loadingLD.value = false
                Log.d("showvoley", result.toString())
                Log.d("showvoley", it)
            },
            {
                Log.d("showvoley", it.toString())
                programLoadErrorLD.value = false
                loadingLD.value = false
            })
        stringRequest.tag = TAG
        queue?.add(stringRequest)

        //username
        queue2 = Volley.newRequestQueue(getApplication())
        val url2 = "http://10.0.2.2/anmp/profile.php"
        val stringRequest2 = object: StringRequest(
            Request.Method.POST, url2,
            {
                Log.d("showvoley", it)
                val sType = object : TypeToken<List<Acc>>() { }.type
                val result = Gson().fromJson<List<Acc>>(it, sType)
                var temp=result as ArrayList<Acc>
                usernameLiveData.value = temp[0].nama_depan
                loadingLD.value = false
                Log.d("showvoley", result.toString())
            },
            {
                Log.d("showvoley", it.toString())
                usernameLoadErrorLD.value = false
                loadingLD.value = false
            })
        {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["id"] = MainActivity.USER_ID.toString()
                return params
            }
        }
        stringRequest2.tag = TAG
        queue2?.add(stringRequest2)
        programLoadErrorLD.value = false
        usernameLoadErrorLD.value = false
        loadingLD.value = false
    }
    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
        queue2?.cancelAll(TAG)
    }
}
