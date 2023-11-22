package com.example.mobile_p12.tugas.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mobile_p12.R
import com.example.mobile_p12.databinding.ActivityLoginBinding
import com.example.mobile_p12.databinding.ActivitySignUpBinding
import com.example.mobile_p12.tugas.AppDatabase
import com.example.mobile_p12.tugas.homepage.HomePageActivity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var mUserDao: UserDao
    private lateinit var executorService : ExecutorService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        executorService = Executors.newSingleThreadExecutor()
        val db = AppDatabase.getDatabase(this)
        mUserDao = db!!.UserDao()!!

        with(binding){
            loginBtLogin.setOnClickListener{
                loginUser(
                    User(email=loginEtEmail.text.toString(), password=loginEtPassword.text.toString())
                )


            }
            loginBtToSignup.setOnClickListener{
                val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun loginUser(user: User) {
        executorService.execute {
            try {
                val loggedInUser = mUserDao.loginUser(user.email, user.password)

                // Check if the returned user is not null, indicating a successful login
                if (loggedInUser != null) {
                    runOnUiThread {
                        showToast("Login successfully")
                    }
                    val intent = Intent(this@LoginActivity, HomePageActivity::class.java)
                    startActivity(intent)
                } else {
                    runOnUiThread {
                        showToast("Invalid email or password")
                    }
                }
            } catch (e: Exception) {
                // Handle the exception and show a Toast with an error message
                runOnUiThread {
                    showToast("Failed to Login")
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}