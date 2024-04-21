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
import com.squareup.picasso.Picasso
import com.ubayadev.informaticsengineeringubaya.model.Acc
import com.ubayadev.informaticsengineeringubaya.model.Program
import com.ubayadev.informaticsengineeringubaya.view.MainActivity
import org.json.JSONObject
import java.time.LocalDateTime

class ProfileViewModel(application: Application): AndroidViewModel(application) {
    val accountLiveData = MutableLiveData<Acc>()
    val accountLoadErrorLD = MutableLiveData<Boolean>()
    val changePasswordLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null
    fun refresh() {
        loadingLD.value = true
        accountLoadErrorLD.value = false
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/anmp/profile.php"
        val stringRequest = object: StringRequest(
            Request.Method.POST, url,
            {
                Log.d("showvoley", it)
                val sType = object : TypeToken<List<Acc>>() { }.type
                val result = Gson().fromJson<List<Acc>>(it, sType)
                var temp=result as ArrayList<Acc>
                accountLiveData.value = temp[0]
                loadingLD.value = false
                Log.d("showvoley", result.toString())
            },
            {
                Log.d("showvoley", it.toString())
                accountLoadErrorLD.value = false
                loadingLD.value = false
            })
        {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["id"] = MainActivity.USER_ID.toString()
                return params
            }
        }
        stringRequest.tag = TAG
        queue?.add(stringRequest)

        accountLoadErrorLD.value = false
        loadingLD.value = false
    }
    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }

    fun changePassword(newPassword:String){
        changePasswordLoadErrorLD.value = false
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/anmp/change_password.php"
        val stringRequest = object: StringRequest(
            Request.Method.POST, url,
            {
                Log.d("showvoley", it)
            },
            {
                Log.d("showvoley", it.toString())
                changePasswordLoadErrorLD.value = false
            })
        {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["id"] = MainActivity.USER_ID.toString()
                params["password"] = newPassword
                return params
            }
        }
        stringRequest.tag = TAG
        queue?.add(stringRequest)
        changePasswordLoadErrorLD.value = false
    }
}