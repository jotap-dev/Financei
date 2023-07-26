package com.example.financei

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.financei.databinding.ActivityAddCountBinding

class AddCountActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityAddCountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityAddCountBinding.inflate(layoutInflater)
        setContentView(this.binding.root)
    }

    override fun onStart() {
        super.onStart()

        binding.button.setOnClickListener {
            finish()
        }

    }
}