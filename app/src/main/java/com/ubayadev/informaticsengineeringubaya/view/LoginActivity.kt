package com.ubayadev.informaticsengineeringubaya.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ubayadev.informaticsengineeringubaya.R
import com.ubayadev.informaticsengineeringubaya.databinding.ActivityLoginBinding
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnRegister.setOnClickListener{
            val intent= Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
        binding.btnLogin.setOnClickListener{
            val q = Volley.newRequestQueue(this)
            val url = "http://10.0.2.2/anmp/login.php"
            val nama = binding.txtUsername.text.toString()
            val password = binding.txtPassword.text.toString()
            val stringRequest = object:StringRequest(
                Request.Method.POST, url,
                {
                    Log.d("apiresult", it.toString())
                    val obj = JSONObject(it)
                    if (obj.getString("result") == "success") {
                        val intent= Intent(this, MainActivity::class.java)
                        MainActivity.USER_ID=obj.getInt("id")
                        startActivity(intent)
                        finish()
                    }
                    else{
                        Toast.makeText(this, "Gagal login", Toast.LENGTH_SHORT).show()
                    }
                },
                {
                    Log.e("apiresult", it.message.toString())
                })
            {
                override fun getParams(): MutableMap<String, String>? {
                    val params = HashMap<String, String>()
                    params["username"] = nama
                    params["password"] = password
                    return params
                }
            }
            q.add(stringRequest)








        }
    }
}