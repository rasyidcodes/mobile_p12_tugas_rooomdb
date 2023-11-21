package com.example.mobile_p12.tugas.editbuku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import com.example.mobile_p12.R
import com.example.mobile_p12.databinding.ActivityEditBukuBinding
import com.example.mobile_p12.databinding.ActivityLoginBinding
import com.example.mobile_p12.tugas.AppDatabase
import com.example.mobile_p12.tugas.homepage.Buku
import com.example.mobile_p12.tugas.homepage.BukuDao
import com.example.mobile_p12.tugas.homepage.HomePageActivity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class EditBukuActivity : AppCompatActivity() {

    private lateinit var binding:ActivityEditBukuBinding
    private lateinit var mBukuDao: BukuDao
    private lateinit var executorService : ExecutorService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBukuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        executorService = Executors.newSingleThreadExecutor()
        val db = AppDatabase.getDatabase(this)
        mBukuDao = db!!.BukuDao()!!

        val id = intent.getStringExtra("id")
        val bukures = intent.getStringExtra("buku")
        val penulisres = intent.getStringExtra("penulis")
        val genreres = intent.getStringExtra("genre")
        val hargares = intent.getStringExtra("harga")

        with(binding){
            editETBuku.text =  Editable.Factory.getInstance().newEditable(bukures)
            editETPenulis.text =  Editable.Factory.getInstance().newEditable(penulisres)
            editETGenre.text =  Editable.Factory.getInstance().newEditable(genreres)
            editETHarga.text =  Editable.Factory.getInstance().newEditable(hargares)

            editBTEdit.setOnClickListener{
                var buku = editETBuku.text.toString()
                var penulis = editETPenulis.text.toString()
                var genre = editETGenre.text.toString()
                var harga = editETHarga.text.toString()

                if (buku == "" || penulis == "" || genre == "" || harga == "" ){
                    showToast("Cant Empty Data!")
                }else{
                    try {
                        val integerHarga = harga.toInt()
                        executorService.execute {
                            try {
                                mBukuDao.update(Buku(buku = buku, penulis = penulis, genre =  genre, harga = integerHarga))


                                // Run the UI code on the main thread to display the Toast
                                runOnUiThread {
                                    showToast("Data Updated successfully")
                                }

                                val intent = Intent(this@EditBukuActivity, HomePageActivity::class.java)
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