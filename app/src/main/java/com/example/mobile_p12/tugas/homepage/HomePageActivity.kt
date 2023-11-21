package com.example.mobile_p12.tugas.homepage

import BukuAdapter
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_p12.R
import com.example.mobile_p12.databinding.ActivityHomePageBinding
import com.example.mobile_p12.tugas.AppDatabase
import com.example.mobile_p12.tugas.addbuku.AddBukuActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HomePageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomePageBinding
    private lateinit var mBukuDao: BukuDao
    private lateinit var executorService : ExecutorService
    private lateinit var bukuAdapter: BukuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        executorService = Executors.newSingleThreadExecutor()
        val db = AppDatabase.getDatabase(this)
        mBukuDao = db!!.BukuDao()!!

        val recyclerView = binding.recyclerview // Replace with your RecyclerView id
        recyclerView.layoutManager = LinearLayoutManager(this)
        bukuAdapter = BukuAdapter()
        recyclerView.adapter = bukuAdapter

        // Fetch and observe buku data
        fetchDataAndObserve()

        with(binding){

            addButton.setOnClickListener{
                val intent = Intent(this@HomePageActivity, AddBukuActivity::class.java)
                startActivity(intent)
            }
        }

    }


    private fun fetchDataAndObserve() {
        // Using CoroutineScope to perform database operations on a background thread
        CoroutineScope(Dispatchers.IO).launch {
            val bukus = mBukuDao.getAllBukus()
            runOnUiThread {
                // Update the UI on the main thread
                bukuAdapter.setBukus(bukus)
            }
        }
    }

    fun showToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, message, duration).show()
    }

}