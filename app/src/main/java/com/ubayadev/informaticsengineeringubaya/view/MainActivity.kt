package com.ubayadev.informaticsengineeringubaya.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ubayadev.informaticsengineeringubaya.R

class MainActivity : AppCompatActivity() {
    companion object{
        var USER_ID=0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}