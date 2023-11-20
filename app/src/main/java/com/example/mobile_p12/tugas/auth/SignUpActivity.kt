package com.example.mobile_p12.tugas.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.mobile_p12.databinding.ActivitySignUpBinding
import com.example.mobile_p12.tugas.AppDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var mUserDao: UserDao
    private lateinit var executorService : ExecutorService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        executorService = Executors.newSingleThreadExecutor()
        val db = AppDatabase.getDatabase(this)
        mUserDao = db!!.UserDao()!!

        with(binding){
            signupBtSignup.setOnClickListener{
                insert(
                    User(email=signupEtEmail.text.toString(), password=signupEtPassword.text.toString())
                )
            }

            signupBtToLogin.setOnClickListener{
                val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                startActivity(intent)
            }

        }
    }

    private fun insert(user: User){
        executorService.execute {
            try {
                mUserDao.insert(user)

                // Run the UI code on the main thread to display the Toast
                runOnUiThread {
                    showToast("Data inserted successfully")
                }
            } catch (e: Exception) {
                // Handle the exception and show a Toast with an error message
                runOnUiThread {
                    showToast("Failed to insert data")
                    Log.d("PEH",e.message.toString())
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}