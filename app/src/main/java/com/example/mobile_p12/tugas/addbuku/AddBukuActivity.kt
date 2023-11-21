package com.example.mobile_p12.tugas.addbuku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.mobile_p12.R
import com.example.mobile_p12.databinding.ActivityAddBukuBinding
import com.example.mobile_p12.databinding.ActivityLoginBinding
import com.example.mobile_p12.tugas.AppDatabase
import com.example.mobile_p12.tugas.homepage.Buku
import com.example.mobile_p12.tugas.homepage.BukuDao
import com.example.mobile_p12.tugas.homepage.HomePageActivity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AddBukuActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddBukuBinding
    private lateinit var mBukuDao: BukuDao
    private lateinit var executorService : ExecutorService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBukuBinding.inflate(layoutInflater)
        setContentView(binding.root)


        executorService = Executors.newSingleThreadExecutor()
        val db = AppDatabase.getDatabase(this)
        mBukuDao = db!!.BukuDao()!!


        with(binding){
            addBTTambah.setOnClickListener{


                    var buku = addETBuku.text.toString()
                    var penulis = addETPenulis.text.toString()
                    var genre = addETGenre.text.toString()
                    var harga = addETHarga.text.toString()

                    if (buku == "" || penulis == "" || genre == "" || harga == "" ){
                        showToast("Cant Empty Data!")
                    }else{
                        try {
                            val integerHarga = harga.toInt()
                            executorService.execute {
                                try {
                                    mBukuDao.insert(Buku(buku = buku, penulis = penulis, genre =  genre, harga = integerHarga))


                                    // Run the UI code on the main thread to display the Toast
                                    runOnUiThread {
                                        showToast("Data inserted successfully")
                                    }

                                    val intent = Intent(this@AddBukuActivity, HomePageActivity::class.java)
                                    startActivity(intent)

                                } catch (e: Exception) {
                                    // Handle the exception and show a Toast with an error message
                                    runOnUiThread {
                                        showToast("Failed to insert data")
                                    }
                                }
                            }
                        } catch (e: NumberFormatException) {
                            showToast("Harga must number!")
                        }
                    }


            }
        }

    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}