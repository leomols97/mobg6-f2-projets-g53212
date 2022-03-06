package com.example.projetremise01

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.projetremise01.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.connect.setOnClickListener {
            connect_button(it)
        }
    }

    private fun connect_button(view: View?)
    {
        val toast = Toast.makeText(applicationContext, "Hello Javatpoint", Toast.LENGTH_SHORT)
        if (!isValidEmail(binding.mailAdress.toString()))
            toast.show()

    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}