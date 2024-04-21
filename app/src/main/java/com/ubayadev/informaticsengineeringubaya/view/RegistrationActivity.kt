package com.ubayadev.informaticsengineeringubaya.view

import android.accounts.Account
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
import com.ubayadev.informaticsengineeringubaya.databinding.ActivityRegistrationBinding
import org.json.JSONObject

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding:ActivityRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnRegister.setOnClickListener{
            if (binding.txtNamaDepan.text.toString()==""){
                Toast.makeText(this, "Isi nama depan", Toast.LENGTH_SHORT).show()
            }
            else if (binding.txtNamaBelakang.text.toString()==""){
                Toast.makeText(this, "Isi nama belakang", Toast.LENGTH_SHORT).show()
            }
            else if (binding.txtUsername.text.toString()==""){
                Toast.makeText(this, "Isi username", Toast.LENGTH_SHORT).show()
            }
            else if (binding.txtPassword.text.toString()==""){
                Toast.makeText(this, "Isi password", Toast.LENGTH_SHORT).show()
            }
            else{
                if (binding.txtPassword.text.toString()==binding.txtConfirmPassword.text.toString()){
                    val q = Volley.newRequestQueue(this)
                    val url = "http://10.0.2.2/anmp/register.php"
                    val stringRequest = object: StringRequest(
                        Request.Method.POST, url,
                        {
                            Log.d("apiresult", it.toString())
                            val obj = JSONObject(it)
                            Toast.makeText(this, obj.getString("result"), Toast.LENGTH_SHORT).show()
                            if (obj.getString("result") == "Account berhasil dibuat") {
                                finish()
                            }
                        },
                        {
                            Log.e("apiresult", it.message.toString())
                        })
                    {
                        override fun getParams(): MutableMap<String, String>? {
                            val params = HashMap<String, String>()
                            params["nama_depan"] = binding.txtNamaDepan.text.toString()
                            params["nama_belakang"] = binding.txtNamaBelakang.text.toString()
                            params["username"] = binding.txtUsername.text.toString()
                            params["password"] = binding.txtPassword.text.toString()
                            return params
                        }
                    }
                    q.add(stringRequest)
                }
                else{
                    Toast.makeText(this, "Konfirmasi password salah", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}