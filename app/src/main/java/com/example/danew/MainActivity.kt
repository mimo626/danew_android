package com.example.danew

import RetrofitClient
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.danew.model.User
import com.example.danew.retrofit.AuthAPI
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.logging.Level
import java.util.logging.Logger


class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initializeComponents()
    }
    private fun initializeComponents() {
        val inputEditID = findViewById<TextInputEditText>(R.id.form_textFieldID)
        val inputEditPassword = findViewById<TextInputEditText>(R.id.form_textFieldPassword)
        val inputEditName = findViewById<TextInputEditText>(R.id.form_textFieldName)
        val inputEditAge = findViewById<TextInputEditText>(R.id.form_textFieldAge)

        val buttonSave = findViewById<MaterialButton>(R.id.form_buttonSave)


        val retrofitClient: RetrofitClient = RetrofitClient()
        val authAPI: AuthAPI = retrofitClient.retrofit!!.create(AuthAPI::class.java)

        buttonSave.setOnClickListener { view: View? ->
            val id = inputEditID.text.toString()
            val password = inputEditPassword.text.toString()
            val name = inputEditName.text.toString()
            val age = inputEditAge.text.toString().toInt() // int라서 다르게 받았음


            val user = User()
            user.id = id
            user.password = password
            user.name = name
            user.age = age
            authAPI.save(user)
                .enqueue(object : Callback<User?> {
                    override fun onResponse(call: Call<User?>, response: Response<User?>) { // 저장이 되었다면
                        Toast.makeText(
                            this@MainActivity,
                            "Save Success",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onFailure(call: Call<User?>, t: Throwable) { // 저장이 실패했다면
                        Toast.makeText(
                            this@MainActivity,
                            "Save failed",
                            Toast.LENGTH_LONG
                        ).show()
                        Logger.getLogger(MainActivity::class.java.name)
                            .log(Level.SEVERE, "Error occurred", t)
                    }
                })
        }
    }
}