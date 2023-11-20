import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_p12.R
import com.example.mobile_p12.tugas.AppDatabase
import com.example.mobile_p12.tugas.homepage.Buku
import com.example.mobile_p12.tugas.homepage.BukuDao
import org.w3c.dom.Text
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class BukuAdapter : RecyclerView.Adapter<BukuAdapter.BukuViewHolder>() {

    private var bukus: List<Buku> = listOf()
    private lateinit var mBukuDao: BukuDao
    private lateinit var executorService : ExecutorService


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BukuViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_buku, parent, false) // Replace with your item layout file
        return BukuViewHolder(view)
    }

    override fun onBindViewHolder(holder: BukuViewHolder, position: Int) {

        executorService = Executors.newSingleThreadExecutor()
        val db = AppDatabase.getDatabase(holder.itemView.context)
        mBukuDao = db!!.BukuDao()!!

        val currentBuku = bukus[position]
        holder.textViewBuku.text = currentBuku.buku
        holder.textViewPenulis.text = "Ditulis oleh : " + currentBuku.penulis
        holder.textViewGenre.text = currentBuku.genre
        holder.textViewHarga.text = "Rp " + currentBuku.harga.toString()

        holder.btEdit.setOnClickListener{
            Toast.makeText(holder.itemView.context, currentBuku.id.toString(), Toast.LENGTH_SHORT).show()
        }

        holder.btDelete.setOnClickListener{
            showYesNoAlertDialog(holder.itemView.context, "Apakah anda yakin akan menghapus " + currentBuku.buku, DialogInterface.OnClickListener { _, _ ->
                Toast.makeText(holder.itemView.context, "del" + currentBuku.id.toString(), Toast.LENGTH_SHORT).show()
            })
        }
    }

    override fun getItemCount(): Int {
        return bukus.size
    }

    fun setBukus(bukus: LiveData<List<Buku>>) {
        bukus.observeForever { bukuList ->
            this.bukus = bukuList
            notifyDataSetChanged()
        }
    }

    inner class BukuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewBuku: TextView = itemView.findViewById(R.id.bukuTextView)
        val  textViewPenulis:TextView = itemView.findViewById(R.id.penulisTextView)
        val  textViewGenre:TextView = itemView.findViewById(R.id.genreTextView)
        val textViewHarga:TextView =itemView.findViewById(R.id.hargaTextView)
        val  btEdit: Button = itemView.findViewById(R.id.itemBtEdit)
        val  btDelete:Button = itemView.findViewById(R.id.itemBtDelete)
        // Add other views if needed
    }

    fun showYesNoAlertDialog(context: Context, message: String, onYesClickListener: DialogInterface.OnClickListener) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setMessage(message)
        alertDialogBuilder.setCancelable(false)

        alertDialogBuilder.setPositiveButton("Yes", onYesClickListener)
        alertDialogBuilder.setNegativeButton("No") { dialog, _ -> dialog.dismiss() }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

}
