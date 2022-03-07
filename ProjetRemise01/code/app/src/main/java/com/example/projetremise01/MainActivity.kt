package com.example.projetremise01

//import android.R
import android.os.Bundle
import android.view.Menu
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.navdrawer_menu, menu)
        return true
    }

    private fun connect_button(view: View?)
    {
        if (!isValidEmail(binding.mailAdress.toString())) {
            val toast = Toast.makeText(applicationContext, "L'adresse mail n'est pas correctement entrée", Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    private fun isValidEmail(email: String): Boolean {
        var pattern = Regex.fromLiteral("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
        print(pattern.matches(email))
        return pattern.matches(email)
    }
}