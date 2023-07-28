package com.example.financei

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.financei.databinding.ActivityAddCountBinding

class AddCountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityAddCountBinding.inflate(layoutInflater)
        setContentView(this.binding.root)
    }

    override fun onStart() {
        super.onStart()

        binding.button.setOnClickListener {

            val editText = binding.editTextText.text.toString()
            val editNumber = binding.editTextNumber.text.toString()

            val replyIntent = Intent()
            if (isNull(editText) || isNull(editNumber)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
                Toast.makeText(
                    this@AddCountActivity,
                    "Error trying to save count",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                replyIntent.putExtra("categoria", editText)
                replyIntent.putExtra("valor", editNumber)
                setResult(Activity.RESULT_OK, replyIntent)
                Toast.makeText(
                    this@AddCountActivity,
                    "Count Added",
                    Toast.LENGTH_SHORT
                ).show()
            }
            finish()
        }

        binding.toBackButton.setOnClickListener {
            val replyIntent = Intent()
            setResult(Activity.RESULT_CANCELED, replyIntent)
            finish()
        }

    }

    private fun isNull(text: String): Boolean {
        return TextUtils.isEmpty(text)
    }
}
